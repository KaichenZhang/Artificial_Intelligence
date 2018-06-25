headTail([H|T],H,T).

all_member([]).
all_member([Head|Tail]) :- Head=_/_/Value,member(Value,[1,0]),all_member(Tail).


replace(Position,[]).
replace(Position,[Head|Tail]) :-
    Position=X/Y/V,
	Head=XS/YS/VS,
	(X=XS,Y=YS
	->VS is V
	;replace(Position,Tail)
	).
	
blockCheker(Position,[]).
blockCheker(Position,[Head|Tail]) :-
    Position=X/Y/V,
	Head=XS/YS/VS,
	(X=XS,Y=YS,VS=0
	->V is 0
	;blockCheker(Position,Tail)
	).
    
   
diag_posiiton(Board,[]).
diag_posiiton(Board,[CellHead|CellTail]) :-
    CellHead = X/Y/V,length(Board,Length),Dimension is round(sqrt(Length)-1),
	%NW
    (  V=1,X1 is X-1,Y1 is Y-1,between(0,Dimension,X1),between(0,Dimension,Y1)
    -> replace(X1/Y1/0,Board)
	;between(0,1,V)
     ),
	 %SW
	 (  V=1,X2 is X-1,Y2 is Y+1,between(0,Dimension,X2),between(0,Dimension,Y2)
    -> replace(X2/Y2/0,Board)
	;between(0,1,V)
     ),
	 %NE
	 (  V=1,X3 is X+1,Y3 is Y-1,between(0,Dimension,X3),between(0,Dimension,Y3)
    -> replace(X3/Y3/0,Board)
	;between(0,1,V)
     ),
	 %SE
	 (  V=1,X4 is X+1,Y4 is Y+1,between(0,Dimension,X4),between(0,Dimension,Y4)
    -> replace(X4/Y4/0,Board)
	;between(0,1,V)
     ),
	 diag_posiiton(Board,CellTail).
	 
	
	
%http://stackoverflow.com/questions/4280986
convtR_C([H|T],[H1|T1]):-
    convtR_C_run([H|T],[H|T],[],[H1|T1],0),!.
convtR_C_run([A|_],_,_,[],N):-length(A,N).
convtR_C_run(P,[],H1,[H1|T1],N):-
    N1 is N+1, convtR_C_run(P,P,[],T1,N1).
convtR_C_run(P,[H|T],L,[H1|T1],N):-
    nth0(N,H,X),
	append(L,[X],L1),
	convtR_C_run(P,T,L1,[H1|T1],N).  
   

 
sumGoal([[]]).
sumGoal([Constraint|Puzzle]) :- 
    headTail(Constraint,A,B),
	headTail(Puzzle,C,D),
	C=[_/_/X1,_/_/X2,_/_/X3,_/_/X4,_/_/X5,_/_/X6,_/_/X7],
	L=[X1,X2,X3,X4,X5,X6,X7],
	sum_list(L,A), 
	append([B],D,R),
	sumGoal(R).




goal(ConstraintR,ConstraintC,Rows) :- 
  append(Rows,ConDomain1),all_member(ConDomain1),
  append([ConstraintR],Rows,Horiz),
  maplist(sumGoal,[Horiz]),diag_posiiton(ConDomain1,ConDomain1),
  convtR_C(Rows,Columns),
  append(Columns,ConDomain2),all_member(ConDomain2),
  append([ConstraintC],Columns,Verti),  
  maplist(sumGoal,[Verti]),diag_posiiton(ConDomain2,ConDomain2).   
  
  
  
move(Position,Board,NextBoard) :-
	blockCheker(Position,Board),
	replace(Position,Board),
	NextBoard is Board.

%idea from Ellis Eghan lab examples	
dfs(ConstraintR,ConstraintC,StartPosition,Counter,Visited, Node, Visited):- goal(ConstraintR,ConstraintC,Node).
dfs(ConstraintR,ConstraintC,StartPosition,Counter,Visited, Node, Path):-
    StartPosition=X/Y/_,
	X1 is X+1,Y1 is Y+1,nth0(Y, ConstraintR,ConsNum),
	%if there are "1" exceeds constraint skip to next line
	( Counter<ConsNum
	-> move(StartPosition,Node, NextNode), \+ member(NextNode, Visited),NextPosition is X1/Y/1,Counter is Counter+1
	;NextPosition is 0/Y1/1,Counter is 0
	),
    dfs(ConstraintR,ConstraintC,NextPosition,Counter,[NextNode|Visited],NextNode,Path).


	
dfs_solver(ConstraintR,ConstraintC,Input, Path):-
    StartPosition=0/0/1,Counter=0,
    dfs(ConstraintR,ConstraintC,StartPosition,Counter,[Input],Input,RevPath),
    reverse(RevPath, Path).












