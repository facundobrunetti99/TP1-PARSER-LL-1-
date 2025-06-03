# Teoría de la Computación
# Proyecto final

## Alumnos:  
- Barroso Gonzalo  
- García Brunetti Facundo  
- Villegas Rodrigo  
- Velasco Benjamín  

---
### Trabajo Práctico 1 - Parser LL(1)

---

**Carrera:** Ingeniería en Sistemas de Información

---

## Ejercicio 1

### No terminales:

`VN = { E , T , F , P , L }`

### Terminales:

`VT = { | , . , ∗ , ( , ) , a , b , c }`

---

### Gramática original

La gramática dada es:

```
E → E | T      ← recursión izquierda
E → T
T → T . F      ← recursión izquierda
T → F
F → P*
F → P
P → (E)
P → L
L → a
L → b
L → c
```

---

### Eliminar recursión izquierda

**¿Qué es la recursión izquierda?**

Una regla como `A → Aα | β` tiene recursión izquierda directa, porque el símbolo no terminal `A` se repite en el lado derecho (de la flecha) al principio.  
Esto no es compatible con parsers LL(1) porque entraríamos en bucles infinitos al hacer análisis descendente.

Queremos convertir:

```
E → E | T
E → T
```

A una forma sin recursión izquierda, apta para LL(1).

Podemos ver que el símbolo `E` tiene dos alternativas:

- `E | T` → recursiva  
- `T` → no recursiva  

Esto se ajusta al formato típico: `A → A α | β`, que significa:

- una producción recursiva (empieza con el mismo no terminal `A`)
- otra que no es recursiva

Ahora aplicamos el método para eliminar recursión izquierda:

```
A → β A'
A' → α A' | λ
```

Entonces en nuestro caso quedaría:

```
E → T E'
E' → | T E' | λ
```

---

Podemos ver que el símbolo `T` tiene dos alternativas:

- `T → T . F` → recursiva  
- `T → F` → no recursiva

Entonces tenemos una situación del tipo: `T → T α | β`, con:

- α = `.F`  
- β = `F`

Entonces lo podemos transformar así:

```
T → F T'
T' → . F T' | λ
```

---

Ahora revisamos el resto de las reglas:

```
F → P* | P
```

Esto no tiene recursión izquierda, pero sí factores comunes.  
Ambas reglas comienzan con `P`.

Usamos factorización por la izquierda:

```
F → P F'
F' → * | λ
```

Las demás:

```
P → (E) | L
L → a | b | c
```

No tienen recursión izquierda ni ambigüedad, así que las dejamos igual.

---

### Gramática final en forma LL(1):

```
E → T E'
E' → | T E'
E' → λ

T → F T'
T' → . F T'
T' → λ

F → P F'
F' → *
F' → λ

P → (E)
P → L

L → a
L → b
L → c
```
