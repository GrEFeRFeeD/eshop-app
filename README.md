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
    "basket": [
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

To get the Facebook Token you need to know the client id of API.
In order to get it, you can perform:

`GET /oauth2/facebook/v15.0`

As a response, you will get:

<pre>
{
    "clientId": "CLIENT_ID",
    "redirectUri": "REDIRECT_URI",
    "tokenRequestUrl": "https://www.facebook.com/v15.0/dialog/oauth?client_id=CLIENT_ID&redirect_uri=REDIRECT_URI&response_type=token"
}
</pre>

Follow the link presented in `tokenRequestUrl`, pass the Facebook authentication, and you will be redirected to `redirectUri` with your facebook token as `access_token` URL param.  


#### Getting the JWT

To perform any authenticated or authorized request you should provide your Facebook Token to the next endpoint:

`POST /authenticate`

With requiered data parameter `{"facebookToken": "EAAHC..."}`.

As the response you will get JSON Web Token as `{"token": "eyJhb..."}`.

The possible exceptions are:
- 401 - bad_facebook_token_exception
- 401 - bad_credentials

Gained JWT should be put in request header as a parameter `Authorization` with value `Bearer eyJhb...`.

#### Edge cases

You can face next exceptions while using JWT within yout request:
- 401 - not_authenticated_exception 
- 401 - bad_token_exception
- 401 - expired_token_exception
- 401 - bad_token_signature_exception
- 401 - malformed_token_exception
- 401 - unsupported_token_exception
- 403 - access_denied_exception

### Implemented API
This section describes all implemented endpoints grouped by users.

#### Guest

Guests can only see the products or authenticate.

##### Get all products list

Request: `GET /products`

Response: list of all [products](#product).

Possible errors: `exceptions do not supossed for this case`.

Supported filters (pass as an request param):
- ?category=category-name - will request with products by given [category](#get-all-categories)

##### Get product by id

Request: `GET /products/{product-id}`

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
- Аутентификация

##### Authentication

Pass your facebook token to next endpoint to get JWT.

Request: `GET /authentication` with body
- `facebookToken` - token obtained from facebook 

Response: `{"token: "bebe...."}`

#### Customer

Manager has the same opportunities as Guest, but also can manage the own profile, basket and review products.

##### Profile managing

###### Get information about yourself

Request: `GET /me`

Response: with apropriate [Customer Dto](#customer)

###### Edit information about yourself

Request: 'POST /me/name' with body:
- `name` - new name for your profile

Response: with apropriate [Customer Dto](#customer)

##### Basket managing

###### Get basket items

Request: `GET /basket`

Response: `{"basket":[ {"product-id": 12, "count": 10} ]}`

###### Add item to basket

Request: 'POST /basket' with body:
- `product-id` - id of product to add
- `count` - count of product to add

Response: `{"basket":[ {"product-id": 12, "count": 10} ]}`

Posible errors:
- product_not_found - in case product by given id was not found
- incorrect_count - in case count is 0 or below

###### Delete item from basket

Request: `DELETE /basket` with body:
- `product-id` - id of product to delete

Response: `{"basket":[ {"product-id": 12, "count": 10} ]}`

Posible errors:
- product_not_found - in case product by given id was not found

##### Product overviewing

###### Get product reviews

Request: `GET /products/{product-id}/reviews`

Response: `{"reviews": { ... }}` - list of [Review Dto](#review)

Possible errors:
- product_not_found - in case product by given id was not found

###### Write own review

Request: `POST /products/{product-id}/reviews` with body:
- `text` - text of review
- `grade` - value in range [1; 5]

Response: with created [Review Dto](#review)

Possible errors:
- product_not_found - in case product by given id was not found

###### Reply to review

Request: `POST /products/{product-id}/reviews/{review-id}` with body:
- `text` - text of comment to review

Response: with affected [Review Dto](#review)

Possible errors:
- product_not_found - in case product by given id was not found
- review_nor_found - in case review by given id was not found

###### Get product questions

Request: `GET /product/{product-id}/questions`

Response: `{"questions": { ... }}` - list of [Question Dto](#question)

Possible errors:
- product_not_found - in case product by given id was not found

###### Ask a question

Request: `POST /product/{product-id}/questions`
 
Response: with created [Question Dto](#question)

Possible errors:
- product_not_found - in case product by given id was not found

#### Manager

Manager has the same opportunities as Guest, but also can manage the products.

- Возможность добавить/удалить/редактировать товар своей категории
##### Product managing

###### Add product

Request: `POST /products` with body:
- `name` - name of product
- `price` - price of product
- `description` - description of product
- `characteristics` - list of product characteristics

Response: with created [Product Dto](#product)

Created product will have the category that was set to user with manager role.

###### Edit product

Request: `POST /products/{product-id}` with body:
- `name` - new name of product
- `price` - new price of product
- `description` - new description of product
- `characteristics` - new list of product characteristics

Response: with edited [Product Dto](#product)

All reviews and questions won't be affected during editing.

Possible errors:
- product_not_found - in case product by given id was not found
- product_incompatible_category - in case product by given id was found but has another from manager's category

###### Delete product

Request: `DELETE /products/{product-id}`

Response: with deleted [Product Dto](#product)

Possible errors:
- product_not_found - in case product by given id was not found
- product_incompatible_category - in case product by given id was found but has another from manager's category

##### Question answering

Request: `POST /products/{product-id}/questions/{question-id}` with body:
- `text` - text to answer the question

Response: with affected [Question Dto](#question)

#### Admin

Admin has the same opportunities as Guest, User or Manager, but also can manage the roles.

##### Role managing

###### Get list of managers

Request: `GET /users/managers`

Response: {"managers":[{...}]} - list of [Manager Dto](#manager)

###### Add / edit manager information

Request: `POST /users/managers` with body:
- `email` - facebook email for an manager
- `category` - one of categories to set

Response: with added / edited [Manager Dto](#manager)

Possible errors:
- user_has_another_role - in case user is exists but has another not-user role

###### Revoke manager role

Request: `DELETE /users/managers/{manager-id}`

Response: with deleted [Manager Dto](#manager)

Possible errors:
- user_not_found - in case user by given id was not found
- user_has_another_role - in case user is exists but has another role

###### Get list of admins

Request: `GET /users/admins`
Response: {"admins":[{...}]} - list of [Admin Dto](#admin)

###### Grant admin role by email

Request: `POST /users/admins` with body:
- `email` - facebook email for an admin

Response: with added / edited [Admin Dto](#admin)

Possible errors:
- user_has_another_role - in case user is exists but has another not-user role

###### Revoke admin role

Request: `DELETE /users/admins/{admin-id}`

Response: with deleted [Admin Dto](#admin)

Possible errors:
- user_not_found - in case user by given id was not found
- user_has_another_role - in case user is exists but has another not-admin role
- user_self_revoking_exception - in case admin is trying to revoke himself
