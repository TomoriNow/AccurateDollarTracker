package attnftasap.adt.controller;

import attnftasap.adt.model.*;
import attnftasap.adt.repository.ExpenseRepository;
import attnftasap.adt.repository.GuardianRepository;
import attnftasap.adt.repository.StudentRepository;
import attnftasap.adt.service.CategoryService;
import attnftasap.adt.service.ExpenseService;
import attnftasap.adt.service.RequestService;
import attnftasap.adt.service.UserService;
import jakarta.servlet.http.HttpSession;
import attnftasap.adt.service.SummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

@Controller
@RequestMapping("/student")
public class ADTController {
    @Autowired
    ExpenseService expenseService;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CategoryService categoryService;

    @GetMapping("/")
    public String getSpendingReportDefault(Model model) {
        LocalDate currentDate = LocalDate.now();
        return "redirect:/student/spendingReport?month="+currentDate.getMonthValue()+"&year="+currentDate.getYear();
    }

    @GetMapping("/spendingReport")
    public String getSpendingReportPage(@RequestParam("month") int month, @RequestParam("year") int year, HttpSession session, Model model) {
        Student student = (Student)  session.getAttribute("userLogin");
        SpendingReport spendingReport = expenseService.getSpendingReport(student, Month.of(month), year);
        LocalDate currentDate = LocalDate.now();
        int startYear = 2023;
        int endYear = currentDate.getYear() + (currentDate.getMonthValue() > 9 ? 1 : 0);

        List<Integer> years = new ArrayList<>();
        for (int yearOption = startYear; yearOption <= endYear; yearOption++) {
            years.add(yearOption);
        }

        model.addAttribute("years", years);
        model.addAttribute("spendingReport", spendingReport);

        return "spendingReport";
    }

    @GetMapping("/saveExpense")
    public String saveExpenseForm(@RequestParam("year") int year, @RequestParam("month") int month, @RequestParam("date") int date, HttpSession session, Model model) {
        Student student = (Student)  session.getAttribute("userLogin");
        Expense expense = new Expense();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month,date);
        expense.setDate(calendar.getTime());
        expense.setStudent(student);
        model.addAttribute("expense", expense);
        return "expenseForm";
    }

    @PostMapping("/saveExpense")
    public String saveExpensePost(@ModelAttribute Expense expense, Model model) {
        expenseService.saveExpense(expense);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(expense.getDate());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        return "redirect:/student/spending_report?month="+month+"&year="+year;
    }

    @PostMapping("/create-category")
    public String createCategory(@RequestParam String categoryName) {
        categoryService.createCategory(categoryName);
        return "redirect:/student/spendingReport";
    }

    @DeleteMapping("/delete-category")
    public String deleteCustomCategory(@ModelAttribute Category category) {
        categoryService.deleteCustomCategory(category.getCategoryUUID());
        return "redirect:/student/spending_report";
    }

    @GetMapping("/guardian-information-page")
    public String getGuardianInformationPage(Model model) {
        Student student = studentRepository.findByUsername("username"); //Placeholder waiting for login logic
        model.addAttribute("student", student);
        return "guardianInformationPage";
    }

    @GetMapping("/invite-page")
    public String getInvitePage(Model model) {
        Student student = studentRepository.findByUsername("username"); //Placeholder waiting for login logic
        model.addAttribute("student", student);
        return "invitePage";
    }
}

@Controller
@RequestMapping("/test")
class TestController {
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    GuardianRepository guardianRepository;

    @Autowired
    ExpenseRepository expenseRepository;

    @GetMapping("/1")
    public ResponseEntity<String> test() {
        Student student = new Student("username", "email", "password");
        Category category = new Category(student, "categoryName", "categoryDescription");
        student.addCategory(category);
        Student owner1 = studentRepository.save(student);
        return ResponseEntity.ok(owner1.toString());
    }

    @GetMapping("/2")
    public ResponseEntity<String> test2() {
        Student student = studentRepository.findByUsername("username");
        Guardian guardian = new Guardian("username2", "email2", "password2");
        guardian.addStudent(student);
        Guardian guardian1 = guardianRepository.save(guardian);
        return ResponseEntity.ok(guardian1.toString());
    }

    @GetMapping("3")
    public ResponseEntity<String> test3() {
        Guardian guardian = guardianRepository.findByUsername("username2");
        Student student = studentRepository.findByUsername("username");
        guardian.removeStudent(student);
        Guardian guardian1 = guardianRepository.save(guardian);
        return ResponseEntity.ok(guardian1.toString());
    }

    @GetMapping("4")
    public ResponseEntity<String> test4() {
        Student student = studentRepository.findByUsername("username");
        return ResponseEntity.ok(student.getCategories().toString());
    }

    @GetMapping("5")
    public ResponseEntity<String> test5() {
        Student student = studentRepository.findByUsername("username");
        Expense expense = new Expense("expense", 500, new Date(), student.getCategories().getFirst(), student);
        expenseRepository.save(expense);
        List<Expense> expenses = expenseRepository.findAllByStudent(student);
        return ResponseEntity.ok(expenses.toString());
    }
}

@Controller
@RequestMapping("/")
class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/login/student")
    public String loginStudentPage(Model model) {
        model.addAttribute("loginRequest", new Student());
        return "studentLogin";
    }

    @GetMapping("/login/guardian")
    public String loginGuardianPage(Model model) {
        model.addAttribute("loginRequest", new Guardian());
        return "guardianLogin";
    }

    @PostMapping("/login/student")
    public String login(@ModelAttribute Student student, HttpSession session, Model model) {
        System.out.println("Login request: " + student);
        Student authenticated = userService.studentAuthenticate(student.getUsername(), student.getPassword());
        if (authenticated != null) {
            session.setAttribute("userLogin", authenticated);
            return "redirect:/student/";
        } else {
            return "error_page";
        }
    }

    @PostMapping("/login/guardian")
    public String login(@ModelAttribute Guardian guardian, HttpSession session, Model model) {
        System.out.println("Login request: " + guardian);
        Guardian authenticated = userService.guardianAuthenticate(guardian.getUsername(), guardian.getPassword());
        if (authenticated != null) {
            session.setAttribute("userLogin", authenticated);
            return "redirect:/guardian/";
        } else {
            return "error_page";
        }
    }

    @GetMapping("/register/student")
    public String registerStudentPage(Model model) {
        model.addAttribute("registerRequest", new Student());
        return "studentRegister";
    }

    @GetMapping("/register/guardian")
    public String registerGuardianPage(Model model) {
        model.addAttribute("registerRequest", new Guardian());
        return "guardianRegister";
    }

    @PostMapping("/register/student")
    public String registerStudent(@ModelAttribute Student student) {
        System.out.println("Register request: " + student);
        Student registeredStudent = userService.registerStudent(student);
        return registeredStudent == null ? "error_page" : "redirect:/login/student";
    }

    @PostMapping("/register/guardian")
    public String registerGuardian(@ModelAttribute Guardian guardian) {
        System.out.println("Register request: " + guardian);
        Guardian registeredGuardian = userService.registerGuardian(guardian);
        return registeredGuardian == null ? "error_page" : "redirect:/login/guardian";
    }
}

@Controller
@RequestMapping("/request")
class GuardianshipRequestController{
    @Autowired
    private RequestService requestService;

    @Autowired
    StudentRepository studentRepository;

    @GetMapping("/{studentId}")
    public List<GuardianshipRequest> getGuardianRequestsByID(@PathVariable UUID studentId) {
        return requestService.getGuardianRequestsByID(studentId);
    }

    @GetMapping("/is-guardian/{studentId}")
    public Guardian getIsGuardianByID(@PathVariable UUID studentId) {
        return requestService.getIsGuardianByID(studentId);
    }

    @PostMapping("/{studentId}/accept")
    public void acceptGuardianRequest(@PathVariable UUID studentId) {
        requestService.removeGuardianByID(studentId, true);
    }

    @PostMapping("/{studentId}/reject")
    public void rejectGuardianRequest(@PathVariable UUID studentId) {
        requestService.removeGuardianByID(studentId, false);
    }
}

@Controller
@RequestMapping("/student")
class SummaryController {

    @Autowired
    private SummaryService summaryService;

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/summary")
    public ResponseEntity<SpendingReport> getSummary (@RequestParam UUID studentId, @RequestParam int year, @RequestParam Month month) {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            SpendingReport spendingReport = summaryService.getSummary(student, month, year);
            return ResponseEntity.ok(spendingReport);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
