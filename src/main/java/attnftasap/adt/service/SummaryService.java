package attnftasap.adt.service;

import attnftasap.adt.model.SpendingReport;
import attnftasap.adt.model.Student;

import java.time.Month;

public interface SummaryService {
    SpendingReport getSummary(Student student, Month month, int year);
}
