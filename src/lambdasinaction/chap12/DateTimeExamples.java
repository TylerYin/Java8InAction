package lambdasinaction.chap12;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.time.temporal.TemporalAdjusters.nextOrSame;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.util.Locale;

public class DateTimeExamples {

	private static final ThreadLocal<DateFormat> formatters = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("dd-MMM-yyyy");
		}
	};

	public static void main(String[] args) {
		useLocalDate();
		useTemporalAdjuster();
		useDateFormatter();
	}

	/**
	 * 对LocalDate，LocalTime，LocalDataTime操作的通用方法如下： 
	 * from, now, of, parse, format, get, minus, plus, with
	 */
	private static void useLocalDate() {
		LocalDate date = LocalDate.of(2014, 3, 18);
		int year = date.getYear();
		Month month = date.getMonth();
		int day = date.getDayOfMonth();
		DayOfWeek dow = date.getDayOfWeek();
		int len = date.lengthOfMonth();
		boolean leap = date.isLeapYear();
		System.out.println(date);

		int y = date.get(ChronoField.YEAR);
		int m = date.get(ChronoField.MONTH_OF_YEAR);
		int d = date.get(ChronoField.DAY_OF_MONTH);

		LocalTime time = LocalTime.of(13, 45, 20);
		int hour = time.getHour();
		int minute = time.getMinute();
		int second = time.getSecond();
		System.out.println(time);

		LocalDateTime dt1 = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45, 20); // 2014-03-18T13:45
		LocalDateTime dt2 = LocalDateTime.of(date, time);
		LocalDateTime dt3 = date.atTime(13, 45, 20);
		LocalDateTime dt4 = date.atTime(time);
		LocalDateTime dt5 = time.atDate(date);
		System.out.println(dt1);

		LocalDate date1 = dt1.toLocalDate();
		System.out.println(date1);
		LocalTime time1 = dt1.toLocalTime();
		System.out.println(time1);
	}

	/**
	 * 如果要对日期进行更加复杂的操作，可以使用重载版本的with方法，向其传入一个TemporalAdjuster对象 来更加灵活地处理日期。
	 * 对于最常见的用例，可以通过TemporalAdjuster类的静态工厂TemporalAdjusters来访问。
	 * 预定义的TemporalAdjuster对象如下所示： 
	 * dayOfWeekInMonth
	 * firstDayOfMonth, firstDayOfNextMonth, firstDayOfNextYear, firstDayOfYear, firstInMonth
	 * lastDayOfMonth, lastDayOfYear, lastInMonth, last/previous
	 * nextOrSame, previousOrSame
	 */
	private static void useTemporalAdjuster() {
		LocalDate date = LocalDate.of(2014, 3, 18);
		date = date.with(nextOrSame(DayOfWeek.SUNDAY));
		System.out.println(date);
		date = date.with(lastDayOfMonth());
		System.out.println(date);

		date = date.with(new NextWorkingDay());
		System.out.println(date);
		date = date.with(nextOrSame(DayOfWeek.FRIDAY));
		System.out.println(date);
		date = date.with(new NextWorkingDay());
		System.out.println(date);

		date = date.with(nextOrSame(DayOfWeek.FRIDAY));
		System.out.println(date);
		date = date.with(temporal -> {
			DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
			int dayToAdd = 1;
			if (dow == DayOfWeek.FRIDAY)
				dayToAdd = 3;
			if (dow == DayOfWeek.SATURDAY)
				dayToAdd = 2;
			return temporal.plus(dayToAdd, ChronoUnit.DAYS);
		});
		System.out.println(date);
	}

	// TemporalAdjuster是一个函数式接口
	private static class NextWorkingDay implements TemporalAdjuster {
		@Override
		public Temporal adjustInto(Temporal temporal) {
			DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
			int dayToAdd = 1;
			if (dow == DayOfWeek.FRIDAY)
				dayToAdd = 3;
			if (dow == DayOfWeek.SATURDAY)
				dayToAdd = 2;
			return temporal.plus(dayToAdd, ChronoUnit.DAYS);
		}
	}

	// 通过使用 DateTimeFormatterBuilder 实现细粒度的日期格式控制
	private static void useDateFormatter() {
		LocalDate date = LocalDate.of(2014, 3, 18);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		DateTimeFormatter italianFormatter = DateTimeFormatter.ofPattern("d. MMMM yyyy", Locale.ITALIAN);

		System.out.println(date.format(DateTimeFormatter.ISO_LOCAL_DATE));
		System.out.println(date.format(formatter));
		System.out.println(date.format(italianFormatter));

		DateTimeFormatter complexFormatter = new DateTimeFormatterBuilder().appendText(ChronoField.DAY_OF_MONTH)
				.appendLiteral(". ").appendText(ChronoField.MONTH_OF_YEAR).appendLiteral(" ")
				.appendText(ChronoField.YEAR).parseCaseInsensitive().toFormatter(Locale.ITALIAN);
		System.out.println(date.format(complexFormatter));
	}
}
