# DICCIONARI DE VARIABLES
CLCOL = 'B'
CACOL = 'C'
INCOL = 'D'
ENTCOL = 'E'
LIMCOL = 'F'


def cambioletra(Numero):
    if Numero == 1:
        letra = 'A'
    elif Numero == 2:
        letra = 'B'
    elif Numero == 3:
        letra = 'C'
    elif Numero == 4:
        letra = 'D'
    elif Numero == 5:
        letra = 'E'
    elif Numero == 6:
        letra = 'F'
    elif Numero == 7:
        letra = 'G'
    elif Numero == 8:
        letra = 'H'
    elif Numero == 9:
        letra = 'I'
    elif Numero == 10:
        letra = 'J'
    elif Numero == 11:
        letra = 'K'
    elif Numero == 12:
        letra = 'L'
    elif Numero == 13:
        letra = 'M'


    return letra
    
def eleccio_centre(centre):
    diccionario = { 'ISOM': 'Is',
                    'Minerva': 'Mi',
                    'Sants': 'Sa',
                    'Urrutia':'Ur',
                    'Centre Obert': 'CO',
                    'Espai Familiar': 'EF',
                    'Santa Coloma': 'Col',
                    'Gorg': 'Go',
                    'Piferrer': 'Pi',
                    'Bufala': 'Bu',
                    'Mediterrania 1': 'M1',
                    'Mediterrania 2': 'M2',
                    'Pisos Lleida': 'PL',
                    'Pisos BCN': 'PB'}
    return diccionario[centre]
