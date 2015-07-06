
This is a scheduling heuristic which takes student's test schedules (with tests represented as integers) and creates test groupings such that there will be no timing conflicts for the students.

Here's a complexity breakdown:

Worst case - every test conflicts with every other. Let t be the number of tests, s be the number of students,
and k be the number of tests each student takes. 

Creating the input array takes O(s*k) time which reduces to O(s) time because k is a constant. 
Populating the HashMap also takes O(s) time. 

The generateSchedule method is the bottleneck.
If every test conflicts with every other, then the outer loop runs t times. 
On the first iteration, the inner loop
also runs t times, which means the intersection method gets called t times. Only the first of these calls takes O(1)
time, the rest take O((s/t)*k) time, where (s/t)*k is the average number of students taking each test. 
On the next iteration of the outer loop the inner loop runs only t-1 times, calling the intersection method t-1 times. 
Again all but one of these calls takes O((s/t)*k) time. So it follows that the worst case time is O(s/t*k( (t-1) + (t-2) +...+ 0) ).
This reduces to O(s/t*k * (t-1)*t/2), which reduces to O(s*t).


Example output:

    Number of students: 17000

    Number of tests: 750

    Num tests per student: 4

    Execution time: 0.366seconds

    Number of test groupings: 61

    First 3 test groupings: 
    [[270, 0, 693, 583, 1, 3, 302, 7, 304, 117, 646, 9, 614, 559, 703, 728, 609],
    [709, 342, 2, 322, 4, 125, 720, 6, 513, 725, 208, 606, 738, 607, 739, 718, 708], 
    [33, 442, 345, 5, 600, 8, 712, 602, 229, 406, 659, 737, 517, 429, 705, 706]]

