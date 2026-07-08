import pandas as pd
import random
from faker import Faker

fake = Faker("es_AR")

categorias = [
    (1, "Notebooks"),
    (2, "Monitores"),
    (3, "Periféricos"),
    (4, "Almacenamiento"),
    (5, "Audio"),
    (6, "Accesorios")
]

df = pd.DataFrame(
    categorias,
    columns=[
        "id_categoria",
        "nombre_categoria"
    ]
)

df.to_csv(
    "./dataset/categorias.csv",
    index=False
)

sucursales = [

(1,"CABA Centro","CABA","Buenos Aires"),

(2,"La Plata","La Plata","Buenos Aires"),

(3,"Rosario","Rosario","Santa Fe"),

(4,"Córdoba Capital","Córdoba","Córdoba"),

(5,"Mendoza Centro","Mendoza","Mendoza")

]

df = pd.DataFrame(
    sucursales,
    columns=[
        "id_sucursal",
        "nombre",
        "ciudad",
        "provincia"
    ]
)

df.to_csv(
    "./dataset/sucursales.csv",
    index=False
)

productos = {
    "Notebooks": {
        "categoria": 1,
        "marcas": ["Lenovo", "Dell", "HP", "Asus", "Acer"],
        "modelos": [
            "IdeaPad 3",
            "Inspiron 15",
            "Pavilion 15",
            "VivoBook 15",
            "Aspire 5"
        ],
        "precio_min": 650000,
        "precio_max": 2300000
    }
}

def generar_productos():
    lista_productos = []
    id_producto = 1
    
    for categoria, datos in productos.items():
        
        for marca in datos["marcas"]:
            
            for modelo in datos["modelos"]:
                lista_productos.append({
                    "id_producto": id_producto,
                    "nombre": modelo,
                    "marca": marca,
                    "id_categoria": datos["categoria"],
                    "precio": random.randint(
                        datos["precio_min"],
                        datos["precio_max"]
                    ),
                    "stock": random.randint(5,100)
                })
                
                id_producto += 1

    df = pd.DataFrame(lista_productos)

    df.to_csv(
        "./dataset/productos.csv",
        index=False
    )

generar_productos()

def generar_clientes():
    clientes = []
    for i in range(1,2001):
        clientes.append({
            "id_cliente": i,
            "nombre": fake.first_name(),
            "apellido": fake.last_name(),
            "edad": random.randint(18,75),
            "sexo": random.choice(["M","F"]),
            "provincia": fake.province(),
            "ciudad": fake.city(),
            "fecha_alta": fake.date_between(start_date="-5y", end_date="today").strftime("%Y-%m-%d")
        })
        
    df = pd.DataFrame(clientes)

    df.to_csv(
        "./dataset/clientes.csv",
        index=False
    )

generar_clientes()

distribucion_empleados = {
    1: 6,
    2: 4,
    3: 4,
    4: 3,
    5: 3
}

def generar_empleados():
    empleados = []
    id_empleado = 1

    for id_sucursal, cantidad in distribucion_empleados.items():
        for i in range(cantidad):
            empleados.append({
                "id_empleado": id_empleado,
                "nombre": fake.first_name(),
                "apellido": fake.last_name(),
                "id_sucursal": id_sucursal,
                "fecha_ingreso": fake.date_between(
                    start_date="-10y",
                    end_date="today"
                )
            })

            id_empleado += 1

    df = pd.DataFrame(empleados)

    df.to_csv(
        "./dataset/empleados.csv",
        index=False
    )

generar_empleados()

def generar_ventas():

    clientes = pd.read_csv("./dataset/clientes.csv")
    productos = pd.read_csv("./dataset/productos.csv")
    empleados = pd.read_csv("./dataset/empleados.csv")

    ventas = []

    medios_pago = [
        "Tarjeta de Crédito",
        "Tarjeta de Débito",
        "Mercado Pago",
        "Transferencia",
        "Efectivo"
    ]

    canales = [
        "Sucursal",
        "Online"
    ]

    for id_venta in range(1,12001):

        producto = productos.sample().iloc[0]
        cliente = clientes.sample().iloc[0]
        empleado = empleados.sample().iloc[0]

        cantidad = random.choices(
            [1,2,3,4,5],
            weights=[70,20,7,2,1]
        )[0]

        descuento = random.choices(
            [0,5,10,15,20,30],
            weights=[55,15,15,8,5,2]
        )[0]

        medio_pago = random.choices(
            medios_pago,
            weights=[45,20,20,10,5]
        )[0]

        canal = random.choices(
            canales,
            weights=[65,35]
        )[0]

        fecha = fake.date_between(
            start_date="-5y",
            end_date="today"
        )

        ventas.append({
            "id_venta": id_venta,
            "fecha": fecha,
            "id_cliente": cliente["id_cliente"],
            "id_producto": producto["id_producto"],
            "id_empleado": empleado["id_empleado"],
            "cantidad": cantidad,
            "precio_unitario": producto["precio"],
            "descuento": descuento,
            "medio_pago": medio_pago,
            "canal": canal
        })

    df = pd.DataFrame(ventas)

    df.to_csv(
        "./dataset/ventas.csv",
        index=False
    )

generar_ventas()

def generar_fecha():

    año = random.randint(2021,2025)

    mes = random.choices(
        [1,2,3,4,5,6,7,8,9,10,11,12],
        weights=[
            8,   # enero
            12,  # febrero
            8,
            8,
            15,  # Hot Sale
            8,
            10,
            8,
            8,
            8,
            10,
            20   # Navidad
        ]
    )[0]

    dia = random.randint(1,28)

    return f"{año}-{mes:02d}-{dia:02d}"

fecha = generar_fecha()