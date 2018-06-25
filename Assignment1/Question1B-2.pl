%facts
%flight(Origin, Destination, Dep_time, Arr_time, Week_day, Airline,Flight_num, NFlying_days, Price, Distance).

%rules
%question2

% Arrive is the name of cities,Node should claim the start city
solver(Node,Arrive,Route/Cost):-
    estimate(Node, EstValue),
    astar(Arrive,[[Node]/0/EstValue], ReverseRoute/Cost/_),
    reverse(ReverseRoute, Route).


%base case and goal state,when the node is the goal	
astar(Arrive,Routes,Route):-
    shortest(Routes,Route),
    Route=[F|_]/_/_,F = flight(_,X,_,_,_,_,_,_,_,_),
    X == Arrive.
	
%idea from Ellis Eghan lab examples	
astar(Arrive,Routes,SolutionRoute):-
    shortest(Routes,BestRoute),
    select(BestRoute, Routes, OtherRoutes),
    expand_astar(BestRoute, ExpRoutes),
    append(OtherRoutes, ExpRoutes, NewRoutes),
    astar(Arrive,NewRoutes,SolutionRoute).

expand_astar(Route,ExpRoutes):-
    findall(NewRoute,move_Astar(Route,NewRoute),ExpRoutes).		


move_Astar([Node|Route]/Cost/_, [NextNode,Node|Route]/NewCost/EstValue):-
	Node = flight(_,YA,DA,_,_,ZA,_,_,_,_),
	NextNode = flight(XB,_,_,AB,_,ZB,_,_,_,_),
	XB=YA,ZA=ZB,AB-DA>=0.5,
    estimate(NextNode, EstValue),
    \+ member(NextNode, Route),
	Cost = ACD/ACH,
	EstValue = EVD/EVH,
	NewCost = NCD/NCH,	
	NCD is ACD + EVD,
%the transition time is 60 minutes
	NCH is ACH + NCH + 1.		


%to find the cheapeast Route	
shortest([Route],Route):-!.
shortest([_|Routes],BestRoute):-shortest(Routes,BestRoute).
%using shortest day to find the "quickest trip"
shortest([Route1/ActualCost1/Est1,_/ActualCost2/Est2|Routes],BestRoute):-
    Est1 = E1D/E1H,Est2 = E2D/E2H,
	ActualCost1 = AC1D/AC1H,
	ActualCost2 = AC2D/AC2H,
    (E1H+AC1H)/24 + E1D + AC1D =< (E2H+AC2H)/24 + E2D + AC2D, !,
    shortest([Route1/ActualCost1,Est1|Routes],BestRoute).

%estimate value is the flight duration of the flight	
estimate([],EstValue) :- EstValue is 0/0.
estimate(F,EstValue) :-
F=flight(_,_,Dep,Arr,_,_,_,Days,_,_),
DepH is floor(Dep/100),ArrH is floor(Arr/100),
DepM is floor(Dep mod 100),ArrM is floor(Arr mod 100),
HeauristicH is (ArrH-DepH) + (ArrM-DepM)/60,
EstValue is Days/HeauristicH.

%?- solver(flight("Montreal VT",_,_,_,_,_,_,_,_,_),"Beijing", Route/Cost).


