# webproject
web application using jdbc, servlet, mysql, tomcat

About the program:

I want to note that the program is designed for the fact that bd initially already exist, but since this
the project will be transferred, I automated the creation and completion of the database.

This program contains the logic for checking for correctness and verification of data relative to the database
data "users". When you register, you are checked for an empty field, matches passwords,
And if such a user is already registered, the corresponding
message-action. Only authorization can be accessed on the main page, where it will be available
2 fields, to select the interval for which the number of days off will be counted, including
holidays.

Algorithm:

The logic of counting the days off and holidays, I divided in half, because with the expansion of this
programs, if you want to count only weekends without holidays - this can be done
for a couple of new lines. "CountHolidays" - contains two main methods, they are
public, because they can be performed separately.

"CalculateFreeDays ()" - counts the number of days off for the range. The cycle works until
day, month, or year of the first date is less than the analogous values ​​of the second date, counts only
entrance days.

"CountOffWeekends" - check if the holidays fall in the range, if they fall, we find out,
how many times it was celebrated not on weekends for the range. To optimize, first cut off those
holidays that go to the right of the interval (which will not exactly fall). Further we look, if
the holiday is less than the initial interval, we take and change the year of our holiday to the one that
is given in the initial interval (so instead of adding and checking by year 1,
whether the holiday falls into a range, we can immediately see whether it has fallen or not and weed out, if they came out
for the maximum interval). In the end, we count the number of holidays not on a day off.

"ConvertToGregorianCalendar" - converts a string to a calendar, it is required to specify that for
more understandable perception, the user enters the month and day in the usual form, but the calendar
counts the month and day.

TreeSet - this collection was chosen on the basis that we will need to store holidays,
Sorted in ascending order, no matter how stored in the database. Additionally
this collection guarantees no duplicates. Since our sort (countOffWeekends),
based on the fact that holidays are kept in ascending order, we can immediately weed out those that are
definitely will not be celebrated, which is a good optimization.

Cons - in this program, in my opinion, when counting the holidays on non-weekend days,
is that we each time the user presses the "count the number of days off
days ", we appeal to the database, in order to fill our ordered collection
(which does not store duplicates) and at the end it is cleaned. This approach has 2 solutions, the first is
the one I applied, the second - to do deep copying (ie create my own copy () method)
which is also not the best solution!
