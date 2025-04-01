(defn binOperation [f]
  (fn [arg1 arg2]
    (mapv f arg1 arg2)))

(defn vectOperation [f v s]
  (mapv #(f % s) v))

(def v+ (binOperation +))
(def v- (binOperation -))
(def v* (binOperation *))
(def vd  (binOperation /))
(defn v*s [v s]
  (vectOperation * v s))
(defn scalar [v1 v2]
  (apply + (v* v1 v2)))
(defn vect [v1 v2]
  (vector (- (* (v1 1) (v2 2)) (* (v1 2) (v2 1)))
          (- (* (v1 2) (v2 0)) (* (v1 0) (v2 2)))
          (- (* (v1 0) (v2 1)) (* (v1 1) (v2 0)))))

(def m+ (binOperation v+))
(def m- (binOperation v-))
(def m* (binOperation v*))
(def md  (binOperation vd))
(defn m*s [m s] (vectOperation v*s m s))
(defn m*v [m v] (vectOperation scalar m v))
(defn transpose [m] (apply mapv vector m))
(defn m*m [m1 m2]
  (mapv #(m*v (transpose m2) %) m1))

(def c4+ (binOperation (binOperation m+)))
(def c4- (binOperation (binOperation m-)))
(def c4* (binOperation (binOperation m*)))
(def c4d (binOperation (binOperation md)))