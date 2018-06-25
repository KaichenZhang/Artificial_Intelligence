%facts
%flight(Origin, Destination, Dep_time, Arr_time, Week_day, Airline,Flight_num, NFlying_days, Price, Distance).

%rules
%question1

headTail([H|T],H,T).

solver(Start,Destination,Airport, AirRoute) :-  
    bfs(Start,Destination,[[Airport]],ReverseRoute),
    reverse(ReverseRoute, AirRoute).

%idea from Ellis Eghan lab examples
bfs(Start,Destination,[[Airport|AirRoute]|_],[Airport|AirRoute]) :- goal(Start,Destination,Airport).

bfs(Start,Destination,[AirRoute|AirRoutes],SolutionPath) :-
    expand_BFS(AirRoute, ExRoutes),
    append(AirRoutes, ExRoutes, NewPaths),  
    bfs(Start,Destination,NewPaths,SolutionPath).
	
expand_BFS([Airport|AirRoute],ExRoutes):-
    findall([NewAirport,Airport|AirRoute], move_noloop(AirRoute, Airport,NewAirport), ExRoutes).

goal(Start,Destination,Solution) :- 
headTail(Solution,H,_),last(Solution,L), 
H=flight(X,_,_,_,_,_,_,_,_,_),X=Start,L=flight(_,Y,_,_,_,_,_,_,_,_),Y=Destination.
	
move_noloop(Visited, Airport, NextAirport):- 
    move(Airport, NextAirport),
    \+ member(NextAirport, Visited).

%next flight should be departe from the destination of previous fligh	
move(Queue, [B,A|Queue]):-  
	A= flight(_,YA,DA,_,_,ZA,_,_,_,_),
	B= flight(XB,_,_,AB,_,ZB,_,_,_,_),
	XB=YA,ZA=ZB,AB-DA>=0.5.



%?- solver("Montreal VT","Beijing",[], AirRoute), last(AirRoute, Solution).