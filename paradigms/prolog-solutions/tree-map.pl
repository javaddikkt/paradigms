node(K, V, Left, Right, H).

map_get(node(K, V, _, _, _), K, V) :- !.
map_get(node(NK, NV, L, R, _), K, V) :-
	K < NK, !,
	map_get(L, K, V).
map_get(node(NK, NV, L, R, _), K, V) :-
	map_get(R, K, V).

insert(nil, K, V, node(K, V, nil, nil, 1)) :- !.
insert(node(K, V1, L, R, H), K, V2, node(K, V2, L, R, H)) :- !.
insert(node(NK, NV, NL, NR, NH), K, V, T) :-
	K < NK,
	insert(NL, K, V, T1),
	balance(node(NK, NV, T1, NR, NH), T),
	!.
insert(node(NK, NV, NL, NR, _), K, V, T) :-
	K > NK,
	insert(NR, K, V, T1),
	balance(node(NK, NV, NL, T1, NH), T),
	!.

build([], Tree, Tree) :- !.
build([(K, V) | T], Tree, ResTree) :-
	insert(Tree, K, V, MidTree),
	build(T, MidTree, ResTree).

map_build(List, Tree) :-
	build(List, nil, Tree).

height(nil, 0) :- !.
height(node(_, _, _, _, H), H).

update_height(node(K, V, L, R, _), node(K, V, L, R, H)) :-
    height(L, HL),
    height(R, HR),
    HL >= HR,
    H is HL + 1,
    !.
update_height(node(K, V, L, R, _), node(K, V, L, R, H)) :-
    height(L, HL),
    height(R, HR),
    H is HR + 1.

diff(node(_, _, L, R, _), D) :-
	height(L, HL),
	height(R, HR),
	D is HL - HR.

rotate_right(node(PK, PV, node(LK, LV, LL, LR, _), R, _), Root) :-
    update_height(node(PK, PV, LR, R, _), PUpd),
    update_height(node(LK, LV, LL, PUpd, _), Root).

rotate_left(node(PK, PV, L, node(RK, RV, RL, RR, _), _), Root) :-
    update_height(node(PK, PV, L, RL, _), PUpd),
    update_height(node(RK, RV, PUpd, RR, _), Root).

balance(Node, BalancedNode) :-
    update_height(Node, UpdatedNode),
    diff(UpdatedNode, D),
    balance_node(D, UpdatedNode, BalancedNode).

balance_node(2, node(K, V, L, R, H), BalancedNode) :-
    diff(L, LD),
    LD >= 0,
    rotate_right(node(K, V, L, R, H), BalancedNode),
    !.
balance_node(2, node(K, V, L, R, H), BalancedNode) :-
    diff(L, LD),
    LD < 0,
    rotate_left(L, L1),
    rotate_right(node(K, V, L1, R, H), BalancedNode),
    !.
balance_node(-2, node(K, V, L, R, H), BalancedNode) :-
    diff(R, RD),
    RD =< 0,
    rotate_left(node(K, V, L, R, H), BalancedNode),
    !.
balance_node(-2, node(K, V, L, R, H), BalancedNode) :-
    diff(R, RD),
    RD > 0,
    rotate_right(R, R1),
    rotate_left(node(K, V, L, R1, H), BalancedNode),
    !.
balance_node(_, Node, Node).

% :NOTE: копипаста
map_lastKey(node(K, _, _, nil, _), K) :- !.
map_lastKey(node(K, _, _, R, _), Key) :-
	map_lastKey(R, Key).

map_lastValue(node(_, V, _, nil, _), V) :- !.
map_lastValue(node(_, _, _, R, _), Value) :-
	map_lastValue(R, Value).


map_lastEntry(node(K, V, _, nil, _), (K, V)) :- !.
map_lastEntry(node(K, V, _, R, _), (Key, Value)) :-
	map_lastEntry(R, (Key, Value)).

map_replace(Map, Key, Value, Result) :-
	map_get(Map, Key, _),
	!,
	insert(Map, Key, Value, Result).
map_replace(Map, Key, Value, Map).
