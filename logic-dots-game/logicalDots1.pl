:- use_module(library(clpfd)). 

headTail([H|T],H,T).
 
sumGoal([[]]).
sumGoal([Constraint|Puzzle]) :- headTail(Constraint,A,B),headTail(Puzzle,C,D),sum(C,#=,A), append([B],D,R),sumGoal(R).

logicDots(ConstraintR,ConstraintC,Rows) :- 
  append(Rows,Domain1),Domain1 ins 0..1,
  append([ConstraintR],Rows,Horiz),
  maplist(sumGoal,[Horiz]),
  transpose(Rows,Columns),
  append(Columns,Domain2),Domain2 ins 0..1,
  append([ConstraintC],Columns,Verti),  
  maplist(sumGoal,[Verti]),             
  maplist(label,Rows).      
 