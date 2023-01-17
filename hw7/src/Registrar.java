import java.util.function.Predicate;
import tester.*;



//a generic list interface
interface IList<T> {

  // do any of the items in this list pass the given predicate?
  boolean ormap(Predicate<T> pred);
  
  // creates a new list of items that pass the given predicate
  IList<T> filter(Predicate<T> pred);
  
  int length();
  
}

//represents an empty generic list
class MtList<T> implements IList<T> {

  // does any element of the empty list satisfy the situation?
  public boolean ormap(Predicate<T> pred) {
    return false;
  }


  // filter this list by the given predicate
  public IList<T> filter(Predicate<T> pred) {
    return this;
  }

  // how long is this list?
  public int length() {
    return 0;
  }
}

//represents a list
class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;

  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }

  // Does any element of the cons list satisfy the situation?
  public boolean ormap(Predicate<T> pred) {
    return pred.test(this.first) || this.rest.ormap(pred);
  }


  
  // filter this list by the given predicate
  public IList<T> filter(Predicate<T> pred) {
    if (pred.test(this.first)) {
      return new ConsList<T>(this.first, this.rest.filter(pred));
    }
    else {
      return this.rest.filter(pred);
    }
  }

// returns the length of a list
  public int length() {
    return 1 + this.rest.length();
  }
}



// represents a course
class Course {
  String name;
  Instructor prof;
  IList<Student> students;

  // constructed with an empty list bc a course is still valid
  // as long as it has a prof (unpopular class or 8am)
  Course(String name, Instructor prof) {
    this.name = name;
    this.prof = prof;
    this.students = new MtList<Student>();
  }

  // EFFECT: adds the given student, s, to the course's
  // list of students
  public void enrollCourse(Student s) {
    this.students = new ConsList<Student>(s, this.students);
  }

}

// represents an prof who teaches a class
class Instructor {
  String name;
  IList<Course> courses;

  Instructor(String name) {
    this.name = name;
    this.courses = new MtList<Course>();
  }

  void enrollProf(Course c) {
    this.courses = new ConsList<Course>(c, this.courses);
  }

  public boolean dejavu(Student s) {
    IList<Course> ahhh = this.courses.filter(new ContainStudent(s));
    int gg = ahhh.length();
    return gg > 1;
  }
}

// represents a student who takes course(s)
class Student {
  String name;
  int id;
  IList<Course> courses;

  Student(String name, int id) {
    this.name = name;
    this.id = id;
    this.courses = new MtList<Course>();
  }


  // EFFECT: adds the course, c, to the students list of courses
  // and calls helper c.enrollCourse(Student s) which will update 
  // this.students in class Course
  public void enroll(Course c) {
    this.courses = new ConsList<Course>(c, this.courses);
    c.enrollCourse(this);
  }

  // does that student, c, share any classes with this student?
  public boolean classmates(Student s) {
    return this.courses.ormap(new ContainStudent(s));
  }
  
  public boolean compareStudents(Student s) {
    return this.id == s.id;
  }

}


class ContainStudent implements Predicate<Course> {
  Student s;

  ContainStudent(Student s) {
    this.s = s;
  }

  // does this list of students contain the student, s?
  public boolean test(Course t) {
    return t.students.ormap(new ContainS(s));
  }
}

class ContainS implements Predicate<Student> {
Student s;

ContainS(Student s) {
  this.s = s;
  
}

  
  /////////////////// rewrite to not have field of field
  @Override
  public boolean test(Student t) {
    return t.compareStudents(this.s);
  }
}

class ExamplesCourse {
  Student s1 = new Student("Phineas", 100);
  Student s2 = new Student("Ferb", 101);
  Student s3 = new Student("Isabella", 102);
  Student s4 = new Student("Candace", 103);
  Student s5 = new Student("Perry", 104);
  Student s6 = new Student("Baljeet", 105);


  // for compare method
  Student s7 = new Student("Baljeet", 105);

  Instructor i1 = new Instructor("Dr Doofenshmirtz");
  Instructor i2 = new Instructor("Vanessa");

  Course c1 = new Course("Building A Rocket", this.i1);
  Course c2 = new Course("Fighting A Mummy", this.i2);
  Course c3 = new Course("Climbing Up the Eiffel Tower", this.i1);
  Course c4 = new Course("Giving A Monkey A Shower", this.i2); 
  
  // list 1 and its sublists
  IList<Student> mt = new MtList<Student>();
  IList<Student> c11 = new ConsList<Student>(this.s1, this.mt);
  IList<Student> c12 = new ConsList<Student>(s3, this.c11);
  IList<Student> c13 = new ConsList<Student>(s4, this.c12);


  // list 2 and its sublists
  IList<Student> c21 = new ConsList<Student>(this.s6, this.mt);
  IList<Student> c22 = new ConsList<Student>(this.s7, this.c21);
  IList<Student> c23 = new ConsList<Student>(this.s5, this.c22);
  IList<Student> c24 = new ConsList<Student>(this.s4, this.c23);


  IList<Course> mtcourse = new MtList<Course>();



  IList<Course> cc21 = new ConsList<Course>(this.c1, this.mtcourse);
  IList<Course> cc22 = new ConsList<Course>(this.c2, this.cc21);
  IList<Course> cc23 = new ConsList<Course>(this.c3, this.cc22);
  IList<Course> cc24 = new ConsList<Course>(this.c4, this.cc23);
  IList<Course> cc25 = new ConsList<Course>(this.c1, this.cc24);
  IList<Course> cc26 = new ConsList<Course>(this.c1, 
      new ConsList<Course>(this.c1, 
          new ConsList<Course>(this.c4,
              new ConsList<Course>(this.c3,
                  new ConsList<Course>(this.c2,
                      new ConsList<Course>(this.c1, this.mtcourse))))));
  // Summers over :(

  void initData() {

    this.s1.enroll(this.c1);
    this.s1.enroll(this.c3);
    // dejavu false

    this.s2.enroll(this.c1);
    this.s2.enroll(this.c2);
    this.s2.enroll(this.c3);
    this.s2.enroll(this.c4);
// dejavu true
    
    this.s3.enroll(this.c1);
    this.s3.enroll(this.c4);
 // dejavu false

    this.s4.enroll(this.c4);
    this.s4.enroll(this.c3);
 // dejavu false

    this.s6.enroll(this.c2);
    this.s6.enroll(this.c1);
    this.s6.enroll(this.c3);
 // dejavu true

    this.i1.enrollProf(this.c1);
    this.i1.enrollProf(this.c3);
    this.i2.enrollProf(this.c2);
    this.i2.enrollProf(this.c4);
    
  }


  void testEnroll(Tester t) {
    this.s4.courses = this.mtcourse;
    this.c4.students = this.mt;
    this.s3.courses = this.mtcourse;

    t.checkExpect(this.s4.courses, this.mtcourse);
    t.checkExpect(this.c4.students, this.mt);


    this.s4.enroll(c4);
    t.checkExpect(this.c4.students.ormap(new ContainS(this.s4)), true);



    this.s3.enroll(c4);
    t.checkExpect(this.c4.students.ormap(new ContainS(this.s3)), true);
  }

  void testEnrollCourse(Tester t) {
    
    this.initData();
    this.s4.courses = this.mtcourse;
    this.c4.students = this.mt;
    this.s3.courses = this.mtcourse;

    t.checkExpect(this.s4.courses, this.mtcourse);
    t.checkExpect(this.c4.students, this.mt);

    this.c4.enrollCourse(s4);
    t.checkExpect(this.c4.students.ormap(new ContainS(this.s4)), true);

    t.checkExpect(this.s3.courses, this.mtcourse);
    this.c4.enrollCourse(s3);
    t.checkExpect(this.c4.students.ormap(new ContainS(this.s3)), true);
  }

  void testEnrollProf(Tester t) {
    this.initData();
    this.i1.courses = this.mtcourse;
    this.i2.courses = this.mtcourse;

    this.i1.enrollProf(c1);
    this.i2.enrollProf(c2);

    t.checkExpect(this.i1.courses, new ConsList<Course>(this.c1, this.mtcourse));
    t.checkExpect(this.i2.courses, new ConsList<Course>(this.c2, this.mtcourse));
  }

  void testClassmates(Tester t) {
    this.initData();
    t.checkExpect(this.s2.classmates(this.s6), true);
    t.checkExpect(this.s2.classmates(this.s5), false);
  }
  
  void testDejavu(Tester t) {
  this.initData();
    t.checkExpect(this.i1.dejavu(this.s1), false);
    t.checkExpect(this.i2.dejavu(this.s2), true);
    t.checkExpect(this.i2.dejavu(this.s4), false);
    t.checkExpect(this.i2.dejavu(this.s5), false);
    t.checkExpect(this.i1.dejavu(this.s6), true);
  }

  boolean testTest(Tester t) {
    this.initData();
    return t.checkExpect(new ContainStudent(this.s1).test(this.c4), false)
        && t.checkExpect(new ContainStudent(this.s4).test(this.c3), true);
  }

}


























