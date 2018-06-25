%facts
sisters(mary,catherine).
sisters(catherine,mary).
brothers(john,simone).
brothers(simone,john).

married(john,mary,2010).
married(mary,john,2010).
married(josh,catherine,2011).
married(catherine,josh,2011).
married(kate,simone,2009).
married(simone,kate,2009).

gave_birth(mary,johnny,2012).
gave_birth(mary,peter,2015).
gave_birth(catherine,william,2012).
gave_birth(kate,betty,2011).

%rules

mother(M,C) :- 
    gave_birth(M,C,_).
father(F,C) :- 
    gave_birth(M,C,_),married(F,M,_).

siblings(A,B) :- 
    sisters(A,B);
    brothers(A,B);
    father(F1,A),father(F2,B), F1=F2,A\=B;
	mother(M1,A), mother(M2,B),M1=M2,A\=B.

cousins(A,B) :- 
    mother(M1,A),mother(M2,B),sisters(M1,M2);
	father(F1,A),father(F2,B),brothers(F1,F2).
	