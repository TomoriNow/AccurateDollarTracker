package attnftasap.adt.controller;

import attnftasap.adt.model.*;
import attnftasap.adt.repository.ExpenseRepository;
import attnftasap.adt.repository.GuardianRepository;
import attnftasap.adt.repository.RequestRepository;
import attnftasap.adt.repository.StudentRepository;
import attnftasap.adt.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/student")
public class ADTController {
    @Autowired
    ExpenseService expenseService;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CategoryService categoryService;
    @Autowired
    SuggestionsService suggestionsService;

    //@Qualifier("requestService")
    @Autowired
    private RequestService requestService;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private BudgetService budgetService;

    @GetMapping("/")
    public String getSpendingReportDefault(Model model) {
        LocalDate currentDate = LocalDate.now();
        return "redirect:/student/spendingReport?month="+currentDate.getMonthValue()+"&year="+currentDate.getYear();
    }

    @GetMapping("/spendingReport")
    public String getSpendingReportPage(@RequestParam("month") int month, @RequestParam("year") int year, HttpSession session, Model model) {
        Student student = (Student)  session.getAttribute("userLogin");
        SpendingReport spendingReport = expenseService.getSpendingReport(student, Month.of(month), year);
        List<Integer> years = getYearOptions();
        List<Integer> dates = getDatesOfMonth(month, year);
        List<Category> categories = categoryService.findAllCategoriesForStudent(student);

        model.addAttribute("categories", categories);
        model.addAttribute("years", years);
        model.addAttribute("student", student);
        model.addAttribute("spendingReport", spendingReport);
        model.addAttribute("dates", dates);
        model.addAttribute("currentSelectedYear", year);
        model.addAttribute("currentSelectedMonth", month);

        return "spendingReportBeta";
    }

    private List<Integer> getYearOptions() {
        LocalDate currentDate = LocalDate.now();
        int startYear = 2023;
        int endYear = currentDate.getYear() + (currentDate.getMonthValue() > 9 ? 1 : 0);

        List<Integer> years = new ArrayList<>();
        for (int yearOption = startYear; yearOption <= endYear; yearOption++) {
            years.add(yearOption);
        }
        return years;
    }

    private List<Integer> getDatesOfMonth(int month, int year) {
        List<Integer> dates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1); // Set to the first day of the specified month

        int actualMaxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int day = 1; day <= actualMaxDay; day++) {
            calendar.set(year, month - 1, day);
            dates.add(day);
        }
        return dates;
    }

    @GetMapping("/saveExpense")
    public String saveExpenseForm(@RequestParam("year") int year, @RequestParam("month") int month, @RequestParam("date") int date, HttpSession session, Model model) {
        Student student = (Student)  session.getAttribute("userLogin");
        Expense expense = new Expense();
        model.addAttribute("expense", expense);
        model.addAttribute("student", student);
        model.addAttribute("year", year);
        model.addAttribute("month", month);
        model.addAttribute("date", date);
        return "expenseForm";
    }

    @PostMapping("/saveExpense")
    public String saveExpensePost(@ModelAttribute("expense") Expense expense,
                                  @RequestParam("year") int year,
                                  @RequestParam("month") int month,
                                  @RequestParam("dateOfMonth") int date,
                                  @RequestParam("categoryName") String categoryName,
                                  HttpSession session) {
        System.out.println(categoryName);
        System.out.println(year);
        System.out.println(month);
        System.out.println(date);
        Student student = (Student)  session.getAttribute("userLogin");
        Category category = categoryService.findCategoryFromStudent(categoryName, student);
        expense.setCategory(category);

        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month-1,date);
        expense.setDate(calendar.getTime());
        expense.setStudent(student);

        expenseService.saveExpense(expense);
        return "redirect:/student/spendingReport?month="+month+"&year="+year;
    }

    @GetMapping("/create-category")
    public String createCategoryPage(Model model) {
        List<String> months = Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
        model.addAttribute("months", months);
        return "createCategory";
    }

    @PostMapping("/create-category")
    public String createCustomCategory(@RequestParam String categoryName,
                                       @RequestParam String description,
                                       @RequestParam String month,
                                       @RequestParam int expectedBudget,
                                       HttpSession session) {
        Student student = (Student) session.getAttribute("userLogin");

        // Get the current year
        int currentYear = Year.now().getValue();

        // Create the category and budget
        categoryService.createCategory(categoryName, description, Month.valueOf(month.toUpperCase()), expectedBudget, student);

        return "redirect:/student/create-category";
    }


    @PostMapping("/delete-category")
    public String deleteCustomCategory(@RequestParam UUID categoryUUID) {
        //LocalDate currentDate = LocalDate.now();
        categoryService.deleteCustomCategory(categoryUUID);
        return "redirect:/student/";
    }
    @GetMapping("/guardian-information-page")
    public String getGuardianInformationPage(Model model) {
        Student student = studentRepository.findByUsername("username"); //Placeholder waiting for login logic
        Guardian guardian = student.getGuardian();
        model.addAttribute("guardian", guardian);
        model.addAttribute("student", student);
        return "guardianInformationPage";
    }

    @PostMapping("/remove-guardian")
    public String removeGuardianFromStudent(@RequestParam UUID studentId) {
        requestRepository.setGuardianNull(studentId);
        return "redirect:/student/guardian-information-page";
    }

    @GetMapping("/invite-page")
    public String getInvitePage(Model model) {
        Student student = studentRepository.findByUsername("username"); // Placeholder waiting for login logic
        List<GuardianshipRequest> guardianshipRequestList = requestService.getGuardianRequestsByID(student.getUserUUID());
        model.addAttribute("student", student);
        model.addAttribute("guardianshipRequestList", guardianshipRequestList);
        return "invitePage";
    }

    @GetMapping("/suggestions")
    public String viewSuggestions(HttpSession session, Model model) {
        Student student = (Student) session.getAttribute("userLogin");
        List<Suggestions> suggestions = suggestionsService.displaySuggestion(student.getUserUUID().toString());
        model.addAttribute("suggestions", suggestions);
        return "studentSuggestions";
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
            System.out.println("User logged in: " + authenticated.getUsername());
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

    @GetMapping("/{studentId}/accept/{requestId}")
    public String acceptGuardianRequest(@PathVariable UUID studentId, @PathVariable UUID requestId) {
        requestService.removeGuardianByID(studentId, requestId, true);
        return "redirect:/student/guardian-information-page";
    }

    @GetMapping("/{studentId}/reject/{requestId}")
    public String rejectGuardianRequest(@PathVariable UUID studentId, @PathVariable UUID requestId) {
        requestService.removeGuardianByID(studentId, requestId, false);
        return "redirect:/student/invite-page";
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

@Controller
@RequestMapping("/suggestions")
class SuggestionsController {

    @Autowired
    private SuggestionsService suggestionsService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private GuardianRepository guardianRepository;

    @GetMapping("/list")
    public String listSuggestions(@RequestParam UUID studentId, HttpSession session, Model model) {
        Guardian guardian = (Guardian) session.getAttribute("userLogin");

        if (guardian != null) {
            List<Student> students = guardian.getStudents();
            List<Suggestions> suggestions = suggestionsService.displaySuggestion(studentId.toString());

            model.addAttribute("students", students);
            model.addAttribute("selectedStudentId", studentId);
            model.addAttribute("suggestions", suggestions);

            return "listSuggestions";
        } else {
            return "error_page"; // Handle the error case
        }
    }

    @GetMapping("/create")
    public String createSuggestionForm(@RequestParam UUID studentId, Model model) {
        Suggestions suggestion = new Suggestions();
        suggestion.setChildUuid(studentId.toString());
        model.addAttribute("suggestion", suggestion);
        return "createSuggestion";
    }

    @PostMapping("/create")
    public String createSuggestionSubmit(@ModelAttribute Suggestions suggestion) {
        suggestionsService.saveSuggestion(suggestion);
        return "redirect:/suggestions/list?studentId=" + suggestion.getChildUuid();
    }
}

@Controller
@RequestMapping("/guardian")
class GuardianController {
    @Autowired
    ExpenseService expenseService;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    GuardianRepository guardianRepository;

    @Autowired
    CategoryService categoryService;
    @Autowired
    SuggestionsService suggestionsService;

    @GetMapping("/")
    public String childReport(Model model) {
        return "childSpending";
    }
}

