"use strict"

// :NOTE: use const
let unary = (f) => (exp) => (x, y, z) => f(exp(x, y, z));

let binary = (f) => (exp1, exp2) => (x, y, z) => f(exp1(x, y, z), exp2(x, y, z));

let cnst = (a) => () => a;

// :NOTE: ok, но можно лучше [] + indexOf
const variables = {'x' : 0, 'y' : 1, 'z' : 2};
let variable = (a) => (...args) => {
    return args[variables[a]];
}

let add = binary((exp1, exp2) => exp1 + exp2)
let subtract = binary((exp1, exp2) => exp1 - exp2)
let multiply = binary((exp1, exp2) => exp1 * exp2)
let divide = binary((exp1, exp2) => exp1 / exp2)
let negate = unary((exp) => -exp)
let cube = unary((exp) => Math.pow(exp, 3))
let cbrt = unary(Math.cbrt)

let pi = cnst(Math.PI);
let e = cnst(Math.E);

// let expr;
// for (let i = 0; i <= 10; i++) {
//     expr = add(
//         subtract(
//             multiply(
//                 variable("x"),
//                 variable("x")),
//             multiply(
//                 cnst(2),
//                 variable("x"))),
//         cnst(1));
//     alert(expr(i))
// }
