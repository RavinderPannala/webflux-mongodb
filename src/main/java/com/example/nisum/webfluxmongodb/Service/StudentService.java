package com.example.nisum.webfluxmongodb.Service;


import com.example.nisum.webfluxmongodb.dto.StudentBasicDto;
import com.example.nisum.webfluxmongodb.dto.StudentDto;
import com.example.nisum.webfluxmongodb.model.Address;
import com.example.nisum.webfluxmongodb.model.Student;
import com.example.nisum.webfluxmongodb.model.Subject;
import com.example.nisum.webfluxmongodb.model.Teachers;
import com.example.nisum.webfluxmongodb.repository.AddressRepository;
import com.example.nisum.webfluxmongodb.repository.StudentRepository;
import com.example.nisum.webfluxmongodb.repository.SubjectRepository;
import com.example.nisum.webfluxmongodb.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuple3;
import reactor.util.function.Tuples;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    TeacherRepository teacherRepository;

    public Mono<Student> save(StudentDto studentDto) {
        Student s = new Student();
        s.setId(studentDto.getId());
        s.setFirstName(studentDto.getFirstName());
        s.setLastName(studentDto.getLastName());
        s.setAge(studentDto.getAge());
        s.setPhoneNumbers(studentDto.getPhoneNumbers());
        Mono<Student> saveMono = studentRepository.save(s);
        List<Address> addressList = new ArrayList<>();
        List<Subject> subjectList = new ArrayList<>();
        List<Teachers> teachersList = new ArrayList<>();
        saveMono.subscribe(student -> {
            studentDto.getAddresses().forEach(add -> {
                Address address = new Address();
                address.setId(add.getId());
                address.setStudentId(add.getStudentId());
                address.setAddress(add.getAddress());
                address.setAddressType(add.getAddressType());
                address.setCity(add.getCity());
                address.setPincode(add.getPincode());
                addressList.add(address);
            });
            studentDto.getSubjects().forEach(sub -> {
                Subject subject = new Subject();
                subject.setId(sub.getId());
                subject.setStudentId(sub.getStudentId());
                subject.setSubjectName(sub.getSubjectName());
                subjectList.add(subject);
            });
            studentDto.getTeachers().forEach(teach -> {
                Teachers teachers = new Teachers();
                teachers.setId(teach.getId());
                teachers.setStudentId(teach.getStudentId());
                teachers.setTeacherName(teach.getTeacherName());
                teachers.setTeacherSubject(teach.getTeacherSubject());
                teachersList.add(teachers);
            });

            Flux<Address> addressFlux = addressRepository.saveAll(addressList);
            Flux<Subject> subjectFlux = subjectRepository.saveAll(subjectList);
            Flux<Teachers> teachersFlux = teacherRepository.saveAll(teachersList);

        });

        return saveMono;
    }

    public Flux<Student> getAll() {
        Flux<Student> studentFlux = studentRepository.findAll().flatMap(student -> {

            Flux<Address> addressFlux = addressRepository.findByStudentId(student.getId()).switchIfEmpty(Flux.just(new Address())).log();
            Flux<Subject> subjectFlux = subjectRepository.findByStudentId(student.getId()).switchIfEmpty(Flux.just(new Subject())).log();
            Flux<Teachers> teachersFlux = teacherRepository.findByStudentId(student.getId()).switchIfEmpty(Flux.just(new Teachers())).log();

            List<Address> addressList = new ArrayList<>();
            List<Subject> subjectList = new ArrayList<>();
            List<Teachers> teachersList = new ArrayList<>();
            addressFlux.collectList().subscribe(addressList::addAll);
            subjectFlux.collectList().subscribe(subjectList::addAll);
            teachersFlux.collectList().subscribe(teachersList::addAll);
            student.setAddress(addressList);
            student.setSubjects(subjectList);
            student.setTeachers(teachersList);

            return Flux.just(student).log();
        }).switchIfEmpty(Flux.just(new Student()));
        return studentFlux;
    }

    public Mono<Student> findById(Long studentId) {
        Mono<Student> map = studentRepository.findById(studentId).zipWhen(student -> {
            Flux<Address> addressFlux = addressRepository.findByStudentId(student.getId()).switchIfEmpty(Flux.just(new Address()));
            Flux<Subject> subjectFlux = subjectRepository.findByStudentId(student.getId()).switchIfEmpty(Flux.just(new Subject()));
            Flux<Teachers> teachersFlux = teacherRepository.findByStudentId(student.getId()).switchIfEmpty(Flux.just(new Teachers()));
            return addressFlux.collectList().zipWith(subjectFlux.collectList()).zipWith(teachersFlux.collectList());
        }).map(tuple2 -> {
            Student student = tuple2.getT1();
            Tuple2<Tuple2<List<Address>, List<Subject>>, List<Teachers>> astCominationTuple = tuple2.getT2();
            Tuple2<List<Address>, List<Subject>> asCombinationTuple = astCominationTuple.getT1();
            List<Teachers> teachersList = astCominationTuple.getT2();
            List<Address> addressLIst = asCombinationTuple.getT1();
            List<Subject> subjectList = asCombinationTuple.getT2();
            student.setAddress(addressLIst);
            student.setSubjects(subjectList);
            student.setTeachers(teachersList);
            return student;
        });
        return map.log();
    }

    public Flux<Student> searchNames(String names) {
        String[] split = names.split(",");
        Flux<Student> studentFlux = Flux.fromArray(split).flatMap(name -> studentRepository.findByFirstName(name));
        return studentFlux;
    }

    public Flux<Tuple2<Tuple2<Tuple2<Student, Address>, Subject>, Teachers>> searchNamesWithZip(String name) {
        Flux<Student> studentByName = studentRepository.findByFirstName(name);

        Flux<Tuple2<Student, Address>> studentAddFlux = studentByName.flatMap(student -> {
            Mono<Address> addressById = addressRepository.findById(student.getId());
            return studentByName.zipWith(addressById);
        });

        Flux<Tuple2<Tuple2<Student, Address>, Subject>> studentAddSubjectFlux = studentAddFlux.flatMap(student -> {
            Flux<Subject> subjectById = subjectRepository.findByStudentId(student.getT1().getId());
            return studentAddFlux.zipWith(subjectById);
        });

        Flux<Tuple2<Tuple2<Tuple2<Student, Address>, Subject>, Teachers>> studentAddSubTeachersFlux = studentAddSubjectFlux.flatMap(student -> {
            Flux<Teachers> teachersById = teacherRepository.findByStudentId(student.getT1().getT1().getId());
            return studentAddSubjectFlux.zipWith(teachersById);
        });

        return studentAddSubTeachersFlux;
    }

    public Flux<Tuple2<String, String>> studentNames() {
        Flux<Student> studentFlux = studentRepository.findAll();
        Flux<String> map = studentFlux.map(student -> {
            return student.getFirstName();
        });
        Flux<String> map1 = studentFlux.map(student -> {
            return student.getLastName();
        });
        Flux<String> map2 = studentFlux.map(student -> {
            return student.getAge();
        });
        Flux<Tuple3<String, String, String>> zip = Flux.zip(map, map1, map2);
        Flux<Tuple2<String, String>> map3 = zip.map(e -> Tuples.of(e.getT1() + e.getT2(), e.getT3()));
        /*Mono<List<String>> listMono = zip.map(string -> {
            return string.getT1().toUpperCase() + " " + string.getT2().toUpperCase() + "----" + string.getT3();
        }).collectList();*/
        //Flux<Tuple2<String, String>> tuple2Flux = map.zipWith(map1);
        return map3;
    }

    public Flux<StudentBasicDto> studentBasicInformation() {
        Flux<StudentBasicDto> studentBasicDtoFlux =studentRepository.findAll().flatMap(student -> {
            Flux<Address> addressFlux = addressRepository.findByStudentId(student.getId());
            Flux<Subject> subjectFlux = subjectRepository.findByStudentId(student.getId());
            Flux<String> stringFlux = subjectFlux.map(sub -> sub.getSubjectName());
            Flux<Teachers> teachersFlux = teacherRepository.findByStudentId(student.getId());
            StudentBasicDto basicDto = new StudentBasicDto();
            basicDto.setFullName(student.getFirstName() + "" + student.getLastName());
            basicDto.setId(student.getId());
            basicDto.setPresentAddress(Mono.from(addressFlux).block());
            basicDto.setFirstTeacher(Mono.from(teachersFlux).block());
            basicDto.setPrimarySubject(Mono.from(stringFlux).block());
            return Flux.just(basicDto);
        });
        return studentBasicDtoFlux;
    }

    //Find Students who are all belogs to Specified location
    public Flux<Student> findStudentsWhoBelogstoSpecifiedLocation(String location){
        List<Address> addresses = new ArrayList<>();
        Flux<Student> studentFlux = studentRepository.findAll().flatMap(student -> {
            Flux<Address> byCity = addressRepository.findByCity(location);

            byCity.collectList().subscribe(addresses::addAll);
            student.setAddress(addresses);
            return Flux.just(student);
        });

        return studentFlux;
    }
}
