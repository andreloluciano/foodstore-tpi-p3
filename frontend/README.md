# Food Store Frontend — TPI Programación 3

Este README corresponde al frontend web del TPI — Food Store.

El proyecto implementa una aplicación web de ecommerce para un negocio de comidas. Permite navegar productos, filtrar por categorías, ver detalle, gestionar un carrito, iniciar sesión con distintos roles y acceder a vistas específicas para cliente o administrador.

## Entrega

- **Video demostrativo:** https://youtu.be/9gj0-A3E6ps
- **Informe PDF:** https://drive.google.com/file/d/1dyVXSi9Kfh4YAq35vSe4ggc-nTbz57D0/view?usp=sharing

---

## Tecnologías

* TypeScript
* Vite
* HTML5
* CSS3
* LocalStorage
* JSON locales

---

## Scripts disponibles

| Comando           | Descripción                                  |
| ----------------- | -------------------------------------------- |
| `npm run dev`     | Levanta el proyecto en modo desarrollo       |
| `npm run build`   | Compila TypeScript y genera la build de Vite |
| `npm run preview` | Previsualiza la build generada               |

---

## Credenciales de prueba

### Administrador

```bash
mail: admin@admin.com
password: 123456
```

### Cliente

```bash
mail: cliente@food.com
password: cliente123
```

---

## Estructura del proyecto

```bash
/
├── index.html
├── package.json
├── tsconfig.json
├── vite.config.ts
├── public/
│   └── data/
│       ├── categorias.json
│       ├── productos.json
│       ├── usuarios.json
│       └── pedidos.json
│
└── src/
    ├── main.ts
    ├── style.css
    │
    ├── types/
    │   ├── IUser.ts
    │   ├── Rol.ts
    │   ├── category.ts
    │   ├── pedido.ts
    │   └── product.ts
    │
    ├── utils/
    │   ├── api.ts
    │   ├── auth.ts
    │   ├── localStorage.ts
    │   └── navigate.ts
    │
    └── pages/
        ├── auth/
        │   ├── login/
        │   │   ├── login.html
        │   │   ├── login.ts
        │   │   └── login.css
        │   └── registro/
        │       ├── registro.html
        │       ├── registro.ts
        │       └── registro.css
        │
        ├── store/
        │   ├── home/
        │   │   ├── home.html
        │   │   └── home.ts
        │   ├── productDetail/
        │   │   ├── productDetail.html
        │   │   └── productDetail.ts
        │   ├── cart/
        │   │   ├── cart.html
        │   │   └── cart.ts
        │   └── store.css
        │
        ├── client/
        │   ├── home/
        │   │   ├── home.html
        │   │   └── home.ts
        │   └── orders/
        │       ├── orders.html
        │       └── orders.ts
        │
        └── admin/
            ├── home/
            │   ├── home.html
            │   └── home.ts
            ├── categories/
            │   ├── categories.html
            │   └── categories.ts
            ├── products/
            │   ├── products.html
            │   └── products.ts
            ├── orders/
            │   ├── orders.html
            │   └── orders.ts
            └── admin.css
```

---

## Qué está implementado

| Componente                                  
| ------------------------------------------- | 
| Login con validación contra `usuarios.json` | 
| Registro de usuario                         | 
| Manejo de sesión con `localStorage`         | 
| Protección de rutas por rol                 | 
| Home / catálogo de productos                | 
| Filtro por categoría                        | 
| Búsqueda de productos                       | 
| Ordenamiento de productos                   | 
| Detalle de producto                         | 
| Agregar productos al carrito                | 
| Validación de stock en detalle y carrito    | 
| Carrito con persistencia en `localStorage`  | 
| Vaciar carrito                              | 
| Vista cliente                               | 
| Historial de pedidos del cliente            | 
| Panel de administración                     | 
| Gestión visual de categorías                | 
| Gestión visual de productos                 | 
| Gestión visual de pedidos                   | 
| Filtro de pedidos por estado                | 

---

## Roles del sistema

### ADMIN

El usuario administrador puede:

* Acceder al panel de administración
* Ver estadísticas generales
* Ver categorías
* Ver productos
* Ver todos los pedidos
* Filtrar pedidos por estado

### USUARIO

El usuario cliente puede:

* Acceder al catálogo
* Buscar productos
* Filtrar por categoría
* Ver detalle de producto
* Agregar productos al carrito
* Gestionar cantidades
* Consultar sus pedidos

---

## Funcionalidades principales

### Login

Ruta:

```bash
src/pages/auth/login/login.html
```

Funcionalidades:

* Formulario de email y contraseña
* Validación de campos
* Búsqueda del usuario en `usuarios.json`
* Guardado de sesión en `localStorage`
* Redirección según rol

---

### Registro

Ruta:

```bash
src/pages/auth/registro/registro.html
```

Funcionalidades:

* Formulario de registro
* Alta simulada de usuario
* Validaciones básicas
* Redirección al login

---

### Store / catálogo

Ruta:

```bash
src/pages/store/home/home.html
```

Funcionalidades:

* Listado de productos
* Búsqueda por nombre
* Filtro por categoría
* Ordenamiento por nombre o precio
* Contador de resultados
* Badge de disponibilidad
* Link al detalle del producto
* Contador del carrito

---

### Detalle de producto

Ruta:

```bash
src/pages/store/productDetail/productDetail.html
```

Funcionalidades:

* Obtiene el producto por ID
* Muestra imagen, nombre, descripción, precio y stock
* Permite seleccionar cantidad
* Valida stock disponible
* Permite agregar al carrito
* No permite agregar productos no disponibles

---

### Carrito

Ruta:

```bash
src/pages/store/cart/cart.html
```

Funcionalidades:

* Lista los productos agregados
* Permite sumar y restar cantidades
* Permite eliminar productos
* Valida stock
* Calcula subtotal y total
* Permite vaciar el carrito
* Guarda los datos en `localStorage`

---

### Cliente / pedidos

Ruta:

```bash
src/pages/client/orders/orders.html
```

Funcionalidades:

* Lista los pedidos del usuario
* Muestra fecha, estado, productos y total
* Permite ver el detalle del pedido
* Usa los datos de `pedidos.json`

---

### Administración

Rutas:

```bash
src/pages/admin/home/home.html
src/pages/admin/categories/categories.html
src/pages/admin/products/products.html
src/pages/admin/orders/orders.html
```

Funcionalidades:

* Dashboard con estadísticas generales
* Listado de categorías
* Listado de productos
* Listado de pedidos
* Filtro de pedidos por estado
* Acceso permitido solo para usuarios con rol `ADMIN`

---

## Flujo recomendado para probar

### Flujo cliente

1. Iniciar sesión como cliente
2. Entrar al catálogo
3. Buscar o filtrar productos
4. Entrar al detalle de un producto
5. Seleccionar cantidad
6. Agregar al carrito
7. Ir al carrito
8. Modificar cantidades
9. Vaciar o revisar el pedido
10. Consultar pedidos del cliente

### Flujo administrador

1. Iniciar sesión como administrador
2. Entrar al panel admin
3. Ver estadísticas generales
4. Ver categorías
5. Ver productos
6. Ver pedidos
7. Filtrar pedidos por estado

---
