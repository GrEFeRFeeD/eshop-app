# eshop-app

## Overview
* [Story](#story)
* [API](#api)
  * [Models](#models)
    * [User](#user)
    * [Comment](#comment)
    * [Review](#review)
    * [Question](#question)
    * [Product](#product)
  * [Authentication & authorization](#authentication--authorization)
  * [Implemented API](#implemented-api)
    * [Guest](#guest)
    * [Customer](#customer-1)
    * [Manager](#manager-1)
    * [Admin](#admin-1)

## Story

Guest:
- Просмотр списка товаров (+фильтры)
- Просмотр отдельного товара (в т.ч. характеристик, отзывов, вопросов)

Customer:
- Все права пользователя Guest
- Возможность управления корзиной (добавить/удалить товары в корзине)
- Возможность писать свои отзывы для определённого товара (оценка + комментарий)
- Возможность писать свои вопросы для определённого товара

Manager:
- Все права пользователя Guest
- Возможность добавить/удалить/редактировать товар своей категории
- Возможность просматривать / комментировать отзывы / вопросы к товару

Admin:
- Все права пользователей User и Manager
- Управление ролями пользователей (дать / упразднить роль Manager, установить категорию менеджера)

Категории товаров и их базовые характеристики:
- Clothes
  - Size
  - Material
  - Brand
- Electronics
  - Type
  - Manufacturer
- Personal Care
  - Type
  - Body part
  - Ingredients
- Hobbies
  - Field
- Equipment
  - Type
- Food Products
  - GMO
  - Lactose contains
  - Peanut contains
- Agriculture
  - Genus
  - Modifications

## API

### Models

#### User

##### Customer
<pre>
{
    "id": 12,
    "email": "bebeb@be.beb",
    "name": "Beb Bebebb",
    "role": "BBBEEE",
    "bascet": [
        {
            "productId": 13,
            "count": 2
        },
        {
            "productId": 14,
            "count": 1
        }
    ]
}
</pre>

##### Manager
<pre>
{
    "id": 12,
    "email": "bebeb@be.beb",
    "name": "Beb Bebebb",
    "role": "BBBEEE",
    "category": "Be"
}
</pre>

##### Admin
<pre>
{
    "id": 12,
    "email": "bebeb@be.beb",
    "name": "Beb Bebebb",
    "role": "BBBEEE",
}
</pre>

#### Comment
Used for commenting reviewes and answearing the questions.
<pre>
{
    "id": 12,
    "user": {
        "id": 12,
        "name": "Beb Bebebb",
        "role": "BBBEEE"
    },
    "text": "be?"
}
</pre>

#### Review
<pre>
{
    "id": 123,
    "user": {
        "id": 12,
        "name": "Beb Bebebb",
        "role": "BBBEEE"
    }
    "text": "b e b e",
    "grade": 4,
    "comments": [
        {
            "id": 12,
            "user": {
                "id": 12,
                "name": "Beb Bebebb",
                "role": "BBBEEE"
            },
            "text": "be?"
        }
    ]
}
</pre>

#### Question
<pre>
{
    "id": 123,
    "user": {
        "id": 12,
        "name": "Beb Bebebb",
        "role": "BBBEEE"
    }
    "text": "b e b e",
    "comments": [
        {
            "id": 12,
            "user": {
                "id": 12,
                "name": "Beb Bebebb",
                "role": "BBBEEE"
            },
            "text": "be?"
        }
    ]
}
</pre>

#### Product:
<pre>
{
    "id": 123,
    "name": "be",
    "price": 123.45,
    "category": "CAT",
    "description": "bebebe bebe bebebebeb",
    "characteristics": [
        {
            "characteristic": "beb",
            "value": "BeBeB"
        },
        {
            "characteristic": "be b be",
            "value": "b b b e"
        }
    ]
    "reviews": []
    "questions": []
}</pre>

### Authentication & authorization
Soon.

### Implemented API
This section describes all implemented endpoints grouped by users.

#### Guest

##### Get all products list

Request: `GET /products`

Response: list of all [products](#product).

Possible errors: `exceptions do not supossed for this case`.

Supported filters (pass as an argument):
- ?category=category-name - will request with products by given [category](#get-all-categories)

##### Get product by id

Request: `Get /products/{product-id}`

Response: with [product dto](#product)

Possible errors: 
- `product_not_found`

##### Get all categories

Request: `GET /categories`

Response: {"categories": ["Category1", "Category2"]}.

Possible errors: `exceptions do not supossed for this case`.

##### Get products by category

- Просмотр списка товаров (+фильтры)
- Просмотр отдельного товара (в т.ч. характеристик, отзывов, вопросов)

#### Customer
- Все права пользователя Guest
- Возможность управления корзиной (добавить/удалить товары в корзине)
- Возможность писать свои отзывы для определённого товара (оценка + комментарий)
- Возможность писать свои вопросы для определённого товара

#### Manager
- Все права пользователя Guest
- Возможность добавить/удалить/редактировать товар своей категории
- Возможность просматривать / комментировать отзывы / вопросы к товару

#### Admin

Admin has the same posibilities as User or Manager, but also can manage the roles.

##### Role managing

###### Get list of managers

Request: `GET /users/managers`
Response: {"managers":[{...}]} - list of [Manager Dto](#Manager)

###### Add / edit manager information

Request: `POST /users/managers` with body:
- `email` - facebook email for an manager
- `category` - one of categories to set

Response: with added / edited [Manager Dto](#Manager)

Possible errors:
- user_has_another_role - in case user is exists but has another not-user role

###### Revoke manager role

Request: `DELETE /users/managers/{manager-id}`

Response: with deleted [Manager Dto](#Manager)

Possible errors:
- user_not_found - in case user by given id was not found
- user_has_another_role - in case user is exists but has another role

###### Get list of admins

Request: `GET /users/admins`
Response: {"admins":[{...}]} - list of [Admin Dto](#Admin)

###### Grant admin role by email

Request: `POST /users/admins` with body:
- `email` - facebook email for an admin

Response: with added / edited [Admin Dto](#Admin)

Possible errors:
- user_has_another_role - in case user is exists but has another not-user role

###### Revoke admin role

Request: `DELETE /users/admins/{admin-id}`

Response: with deleted [Admin Dto](#Admin)

Possible errors:
- user_not_found - in case user by given id was not found
- user_has_another_role - in case user is exists but has another not-admin role
- user_self_revoking_exception - in case admin is trying to revoke himself
