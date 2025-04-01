is_divider(X, Y) :- 0 is mod(X, Y).

divides(X, Y) :- is_divider(X, Y), !.
divides(X, Y) :- YY is Y * Y, YY =< X, Y1 is Y + 1, divides(X, Y1).

first_divider(X, Y, R) :- is_divider(X, Y), prime(Y), R is Y, !.
first_divider(X, Y, R) :- Y1 is Y + 1, Y1 =< X, first_divider(X, Y1, R), !.

prime(2) :- !.
prime(X) :- X > 1, \+ divides(X, 2), !.

composite(X) :- X > 1, \+ prime(X).

find_divs(1, Divs, Divs) :- !.
find_divs(X, Divs, R) :- first_divider(X, 2, D), X1 is div(X, D), append(Divs, [D], R1), find_divs(X1, R1, R). 

prime_divisors(X, Divs) :- find_divs(X, [], Divs).

find_prime(A, 2, N, 1) :- !.
find_prime(A, Z, N, R) :- prime(Z), B is A + 1, B is Z, R is N + 1, !.
find_prime(A, Z, N, R) :- prime(Z), B is A + 1, prime(B), N1 is N + 1, find_prime(B, Z, N1, R), !.
find_prime(A, Z, N, R) :- prime(Z), B is A + 1, find_prime(B, Z, N, R).

prime_index(X, R) :- find_prime(2, X, 1, R).