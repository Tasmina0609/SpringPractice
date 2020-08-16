package net.spingboot.java.controller;

import net.spingboot.java.entity.Student;
import net.spingboot.java.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/students/")
public class StudentController {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentController(StudentRepository studentRepository) {
        this.studentRepository=studentRepository;
    }

    @GetMapping("signup")
    public String showSignUpForm() {
        return "addStudent";
    }

    @GetMapping("list")
    public String students(Model model) {
        model.addAttribute("students",this.studentRepository.findAll());
        return "index";
    }

    @PostMapping("add")
    public String addStudent(@Valid Student student, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "addStudent";
        }
        this.studentRepository.save(student);
        return "redirect:list";  // ??????????????
    }

    @GetMapping("edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id,Model model) {
        Student student=this.studentRepository.findById(id).
                orElseThrow(()-> new IllegalArgumentException("Invalid Student id: "+id));
        model.addAttribute("student",student);
        return "updateStudent";
    }

    @PostMapping("update/{id}")
    public String updateStudent(@PathVariable("id") long id,Student student,BindingResult result,Model model) {
        if(result.hasErrors()) {
            student.setId(id);
            return "updateStudent";
        }
        // update
        this.studentRepository.save(student);

        // get all students from ( with update )
        model.addAttribute("students",this.studentRepository.findAll());
        return "index";
    }

    @GetMapping("delete/{id}")
    public String deleteStudent(@PathVariable("id") long id,Model model) {
        Student student=this.studentRepository.findById(id).
                orElseThrow(()-> new IllegalArgumentException("Invalid Student id: "+id));
        this.studentRepository.delete(student);
        model.addAttribute("student",this.studentRepository.findAll());
        return "index";
    }

}
