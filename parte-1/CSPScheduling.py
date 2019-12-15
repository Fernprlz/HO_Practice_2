#!/usr/bin/env python
# -*- coding: utf-8 -*-

from constraint import *
problem = Problem()

#              0     1      2    3      4     5     6     7     8     9    10    11    12    13    14
indiceDias = [None, 'l1', 'l2', 'l3', None, 'm1', 'm2', 'm3', None, 'x1', 'x2', 'x3', None, 'j1', 'j2']
# indice de asignaturas --- ['N', 'S', 'L', 'M', 'I', 'F'])
# ------------------------- [ 20,  21,  22,  23,  24,  25)

# Cada hora del dia tiene valores consecutivos, pero el primer valor del dia siguiente
# y el ultimo del actual

####### V A R I A B L E S / D O M I N I O S #######
# Asignaturas
problem.addVariables(['N1', 'N2', 'L1', 'L2',  'I1', 'I2', 'F1'],
                     [1, 2, 3, 5, 6, 7, 9, 10, 11, 13, 14])

problem.addVariables(['M1', 'M2'],
                    [1, 5, 9, 13])

problem.addVariables(['S1', 'S2'],
                    [3, 7, 11, 14])


# Profesores
problem.addVariables(['C1', 'C2', 'A1', 'A2', 'U1', 'U2'],
                     ['N', 'S', 'L', 'M', 'I', 'F'])

############ R E S T R I C C I O N E S ############

# 1. Las clases de naturales deben ser consecutivas y en el mismo día
def consecutive(a, b):
    result = False
    diferencia = abs(a-b)
    if diferencia == 1:
        # Los dias son consecutivos
        result = True
    return result

problem.addConstraint(consecutive, ('N1', 'N2'))

# 2. Mates no puede ser el mismo día que naturales
def sameDay(a, b):
    result = False
    diaA = indiceDias[a][0]
    diaB = indiceDias[b][0]

    if diaA != diaB:
        # Los dias son distintos
        result = True
    return result

problem.addConstraint(sameDay, ('M1', 'N1'))
problem.addConstraint(sameDay, ('M1', 'N2'))
problem.addConstraint(sameDay, ('M2', 'N1'))
problem.addConstraint(sameDay, ('M2', 'N2'))

# 3. Mates no puede ser el mismo día que inglés
problem.addConstraint(sameDay, ('M1', 'I1'))
problem.addConstraint(sameDay, ('M1', 'I2'))
problem.addConstraint(sameDay, ('M2', 'I1'))
problem.addConstraint(sameDay, ('M2', 'I2'))

# 4. Cada profesor tiene que tener 2 asignaturas, y no puede haber dos profesores con la misma asignatura.
# [Al final]

# . Lucía solo se encarga de sociales si Andrea se encarga de Educacion Fisica
def pickyLucia(a, b):
    result = False
    # Lucia se encarga de Sociales si Andrea se encarga de EF
    if (a == 'S' and b == 'F') or (a != 'S'):
        result = True
    return result

problem.addConstraint(pickyLucia, ('C1', 'A1'))
problem.addConstraint(pickyLucia, ('C1', 'A2'))
problem.addConstraint(pickyLucia, ('C2', 'A1'))
problem.addConstraint(pickyLucia, ('C2', 'A2'))

# 5. Juan no da clase ni lunes ni jueves a primera

def pickyJuanSoc(a, b):
    result = True
    if (b==1 or b==13):
        if (a == 'S'):
            result = False
    return result

def pickyJuanNat(a, b):
    result = True
    if (b==1 or b==13):
        if (a == 'N'):
            result = False
    return result

problem.addConstraint(pickyJuanSoc, ('U1', 'S1'))
problem.addConstraint(pickyJuanSoc, ('U1', 'S2'))
problem.addConstraint(pickyJuanSoc, ('U2', 'S1'))
problem.addConstraint(pickyJuanSoc, ('U2', 'S2'))

problem.addConstraint(pickyJuanNat, ('U1', 'N1'))
problem.addConstraint(pickyJuanNat, ('U1', 'N2'))
problem.addConstraint(pickyJuanNat, ('U2', 'N1'))
problem.addConstraint(pickyJuanNat, ('U2', 'N2'))

# 7. Dos variables no pueden tener el mismo dominio
problem.addConstraint(AllDifferentConstraint())


# Obtenemos la solucion
print(problem.getSolutions())
