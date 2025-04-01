(defn parse [cnst vrb mapOp expr]
  (cond
    (number? expr) (cnst expr)
    (symbol? expr) (vrb (str expr))
    :else (apply (mapOp (first expr)) (mapv #(parse cnst vrb mapOp %) (rest expr)))
    )
  )

;--------------------------------------------------------------------------------
;                               HW-10
;--------------------------------------------------------------------------------
; :NOTE: Объединить с unOperation (+)
(defn binOperation [op]
  (fn [exp1 exp2]
    (fn [args]
      (op (exp1 args) (exp2 args)))))

(defn operation [op]
  (fn [& exp]
    (fn [args]
      (apply op (mapv #(% args) exp)))))

(def add (operation +))
(def subtract (operation -))
(def multiply (operation *))
(def divide (operation (fn [a b] (/ (double a) b))))
(def negate (operation -))
(def sinh (operation (fn [a] (Math/sinh a))))
(def cosh (operation (fn [a] (Math/cosh a))))

; :NOTE: constantly (+)
(def constant constantly)

(defn variable [name]
  (fn [args]
    (args name)))

(def operations {'+      add,
                 '-      subtract,
                 '*      multiply,
                 '/      divide,
                 'negate negate,
                 'sinh   sinh,
                 'cosh   cosh})

(defn parseFunction [exp]
  (parse constant variable operations (read-string exp)))

;--------------------------------------------------------------------------------
;                               HW-11
;--------------------------------------------------------------------------------
(load-file "proto.clj")
(use '[clojure.string :only (join)])

(def leftExpr (field :leftExpr))
(def rightExpr (field :rightExpr))
(def sign (field :sign))
(def op (field :op))
(def value (field :value))
(def expressions (field :expressions))

(def toStringPostfix (method :toStringPostfix))
(def evaluate (method :evaluate))
(def toString (method :toString))

(def operationObj
  {
   :evaluate (fn [expr vars] (apply (op expr) (mapv #(evaluate % vars) (expressions expr)))),
   :toString (fn [expr] (str "(" (sign expr) " " (join " " (mapv #(toString %) (expressions expr))) ")")),
   :toStringPostfix (fn [expr] (str "(" (join " " (mapv #(toStringPostfix %) (expressions expr))) " " (sign expr) ")"))
   })

(def constantObj
  {
   :evaluate (fn [expr _] (value expr)),
   :toString (fn [expr] (str (value expr))),
   :toStringPostfix (fn [expr] (str (value expr)))
   })

(def variableObj
  {
   :evaluate (fn [expr vars] (vars (str (clojure.string/lower-case (first (value expr)))))),
   :toString (fn [expr] (value expr)),
   :toStringPostfix (fn [expr] (value expr))
   })

(defn constructor [sign op]
  (fn [& args] {:prototype operationObj :expressions args :sign sign :op op}))

(defn Constant [value] (assoc constantObj :value value))
(defn Variable [name] (assoc variableObj :value name))
(def Add (constructor "+" +))
(def Subtract (constructor "-" -))
(def Multiply (constructor "*" *))
(def Divide (constructor "/" (fn [a b] (/ (double a) b))))
(def Pow (constructor "pow" (fn [a b] (Math/pow a b))))
(def Log (constructor "log" (fn [a b] (/ (Math/log (double (Math/abs b))) (Math/log (Math/abs a))))))
(def Negate (constructor "negate" -))
(def Min (constructor "min" (fn [& args] (apply min args))))
(def Max (constructor "max" (fn [& args] (apply max args))))


(def operationsObj {'+      Add
                    '-      Subtract
                    '*      Multiply
                    '/      Divide
                    'negate Negate
                    'pow    Pow
                    'log    Log
                    'min    Min
                    'max    Max
                    })

(defn parseObject [exp]
  (parse Constant Variable operationsObj (read-string exp)))

;--------------------------------------------------------------------------------
;                               HW-12
;--------------------------------------------------------------------------------
(load-file "parser.clj")


(defn +word [s]
  (+map
    (partial apply str)
    (apply +seq (mapv (comp +char str) s))))

(def +number
  (+seqf
    (partial apply str)
    (+opt (+char "-")) (+plus (+char "0123456789."))))


(def *ws (+ignore (+star (+char " \t\n\r"))))

(def *operator
  (+map
    #((symbol %) operationsObj)
    (apply +or (mapv +word (mapv str (keys operationsObj))))))

(def *variable
  (+map
    Variable
    (+str (+plus (+char "xyzXYZ")))))

(def *constant
  (+map
    (comp Constant read-string)
    (+str +number)))


(declare *getToken)

(def *expression
  (+map #(apply (second %) (first %))
        (+seq
          (+ignore (+char "("))
          (+star (delay (*getToken)))
          *operator
          *ws
          (+ignore (+char ")"))
          )))

(def *token
  (+map
    first
    (+seq *ws (+or *constant *variable *expression) *ws)))

(defn *getToken [] *token)


(def parseObjectPostfix (+parser *token))