"use strict";

const operations = {}
const Operation = function (f, symbol) {
    const Operation = function (...args) {
        this.args = args
    }
    Operation.prototype = {
        toString: function () {
            return this.args.join(" ") + " " + symbol
        },
        prefix: function () {
            return "(" + symbol + " " + this.args.map(arg => arg.prefix()).join(" ") + ")"
        },
        evaluate: function (x, y, z) {
            return f(...this.args.map(arg => arg.evaluate(x, y, z)))
        }
    }
    operations[symbol] = [Operation, f.length]
    return Operation
}

const Product = Operation(
    function (...args) {
        let mul = 1;
        for (const arg of args) {
            mul *= arg
        }
        return mul
    },
    "product",
)
const Geom = Operation(
    function (...args) {
        let mul = 1
        for (const arg of args) {
            mul *= arg
        }
        mul = Math.abs(mul)
        return Math.pow(mul, 1 / args.length)
    },
    "geom",
)

const Add = Operation((a, b) => (a + b), "+")
const Subtract = Operation((a, b) => (a - b), "-")
const Multiply = Operation((a, b) => (a * b), "*")
const Divide = Operation((a, b) => (a / b), "/")
const Negate = Operation((a) => (-a), "negate")
const Sinh = Operation(Math.sinh, "sinh")
const Cosh = Operation(Math.cosh, "cosh")

const Const = function (val) {
    this.val = val
}
Const.prototype = {
    toString: function () {
        return String(this.val)
    },
    prefix: function () {
        return String(this.val)
    },
    postfix: function () {
        return String(this.val)
    },
    evaluate: function (x, y, z) {
        return Number(this.val)
    }
}

const vars = ['x', 'y', 'z']
const Variable = function (name) {
    this.name = name
}
Variable.prototype = {
    toString: function () {
        return String(this.name)
    },
    prefix: function () {
        return String(this.name)
    },
    postfix: function () {
        return String(this.name)
    },
    evaluate: function (...args) {
        return args[vars.indexOf(this.name)]
    }
}

// :NOTE: vars? vars.indexOf()
const variables = {'x': new Variable('x'), 'y': new Variable('y'), 'z': new Variable('z')}
const parse = function (line) {
    const arr = line.split(' ')
    const stack = []
    for (const token of arr) {
        if (token in variables) {
            stack.push(variables[token])
        } else if (token in operations) {
            if (operations[token][1] === 2) {
                const b = stack.pop()
                const a = stack.pop()
                stack.push(new operations[token][0](a, b))
            } else {
                const a = stack.pop()
                stack.push(new operations[token][0](a))
            }
        } else if (!(token === "")) {
            stack.push(new Const(token))
        }
    }
    return stack.pop()
}

function ExpressionError(message) {
    this.message = message
}

ExpressionError.prototype = Object.create(Error.prototype)
ExpressionError.prototype.name = "Expression error"
ExpressionError.prototype.constructor = ExpressionError

const check = function (line) {
    let balance = 0
    for (const c of line) {
        if (c === '(') {
            balance++
        } else if (c === ')') {
            balance--
        }
        if (balance < 0) {
            throw new ExpressionError("wrong brackets sequence")
        }
    }
    if (balance > 0) {
        throw new ExpressionError("wrong brackets sequence")
    }
}

const parsePrefix = function (line) {
    check(line)
    line = line.replaceAll('(', ' ')
    line = line.replaceAll(')', ') ')
    const arr = line.split(' ').reverse()
    const stack = []
    let open = false
    for (let t of arr) {
        let token = String(t)
        if (token.charAt(token.length - 1) === ')') {
            stack.push(')')
            token = token.substring(0, token.length - 1)
        }
        if (token in variables) {
            stack.push(variables[token])
        } else if (token in operations) {
            let args = []
            let arg = stack.pop()
            while (arg !== ')' && stack.length > 0) {
                args.push(arg)
                arg = stack.pop()
            }
            const cnt = operations[token][1]
            if (cnt !== 0 && args.length !== cnt) {
                throw new ExpressionError("argument missing for >>>" + token + "<<<")
            }
            if (arg !== ')') {
                throw new ExpressionError("missing operand for >>>" + args[args.length - 1].toString() + "<<<")
            }
            stack.push(new operations[token][0](...args))
        } else if (token !== "" && !isNaN(Number(token))) {
            stack.push(new Const(token))
        } else if (token !== "") {
            throw new ExpressionError("illegal symbol in >>>" + token + "<<<")
        }
    }
    if (stack.length === 0) {
        throw new ExpressionError("empty input")
    }
    if (stack.length > 1) {
        throw new ExpressionError("missing operand for >>>" + +stack[1] + "<<<")
    }
    const ans = stack.pop()
    if (ans === ')') {
        throw new ExpressionError("missing operand")
    }
    return ans
}
