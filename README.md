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

## Story

Guest:
- Просмотр списка товаров (+фильтры)
- Просмотр отдельного товара (в т.ч. характеристик, отзывов, вопросов)

User:
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
  - Made in
- Electronics
  - be
- Personal Care
  - be
- Hobbies
  - be
- Equipment
  - be
- Food Products
  - be
- Agriculture
  - be

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

### Implemented API
