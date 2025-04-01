:- load_library('alice.tuprolog.lib.DCGLibrary').

nonvar(V, _) :- var(V).
nonvar(V, T) :- nonvar(V), call(T).

find(K, [(K, V) | _], V) :- !.
find(K, [_ | T], R) :- find(K, T, R).

operation(op_add, A, B, R) :- R is A + B.
operation(op_subtract, A, B, R) :- R is A - B.
operation(op_multiply, A, B, R) :- R is A * B.
operation(op_divide, A, B, R) :- R is A / B.
operation(op_negate, A, R) :- R is 0 - A.
operation(op_cube, A, R) :- R is A ** 3.
operation(op_cbrt, A, R) :- 
	A < 0,
	A1 is -A,
	B is 1 / 3,
	RM is A1 ** B,
	R is -RM.
operation(op_cbrt, A, R) :- 
	A >= 0,
	B is 1 / 3,
	R is A ** B.

evaluate(const(Value), _, Value).
evaluate(variable(Name), Variables, R) :- 
	atom_chars(Name, [First | T]), 
	find(First, Variables, R).
evaluate(operation(Op, A), Variables, R) :- 
	evaluate(A, Variables, AV),
	operation(Op, AV, R).
evaluate(operation(Op, A, B), Variables, R) :-
	evaluate(A, Variables, AV),
	evaluate(B, Variables, BV),
	operation(Op, AV, BV, R).

op_p(op_add) --> ['+'].
op_p(op_subtract) --> ['-'].
op_p(op_multiply) --> ['*'].
op_p(op_divide) --> ['/'].
op_p(op_negate) --> {atom_chars(negate, S)}, S.
op_p(op_cube) --> {atom_chars(cube, S)}, S.
op_p(op_cbrt) --> {atom_chars(cbrt, S)}, S.

digits_p([]) --> [].
digits_p([H | T]) -->
  { member(H, ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', '-'])},
  [H],
  digits_p(T).

name_p([]) --> [].
name_p([H | T]) -->
	{member(H, [x, y, z, 'X', 'Y', 'Z'])},
	[H],
	name_p(T).

ws_s --> [].
ws_s --> [' '], ws_s.
ws_p --> [' '], ws_s.

expr_p(variable(Name)) --> 
	{nonvar(Name, atom_chars(Name, Chars))},
	ws_s, name_p(Chars), ws_s, 
	{Chars = [_ | _], atom_chars(Name, Chars)}.
expr_p(const(Value)) --> 
	{nonvar(Value, number_chars(Value, Chars))},
  ws_s, digits_p(Chars), ws_s,
  {Chars = [_ | _], \+ Chars = ['-'], number_chars(Value, Chars)}.
expr_p(operation(Op, A)) --> ws_s, ['('], ws_s, op_p(Op), ws_p, expr_p(A), ws_s, [')'], ws_s.
expr_p(operation(Op, A, B)) --> ws_s, ['('], ws_s, op_p(Op), ws_p, expr_p(A), ws_p, expr_p(B), ws_s, [')'], ws_s.

prefix_str(E, A) :- ground(E), phrase(expr_p(E), C), atom_chars(A, C), !.
prefix_str(E, A) :- atom(A), atom_chars(A, C), phrase(expr_p(E), C), !.

