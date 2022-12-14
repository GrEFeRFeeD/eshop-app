# eshop-app

## Overview
* [Story](#story)
* [API](#api)
  * [Models](#models)
    * [Users](#user)
    * [Comment](#comment)
    * [Category](#category)
    * [Review](#review)
    * [Question](#question)
    * [Product](#product)
  * [Exceptions](#exceptions)
  * [Authentication & authorization](#authentication--authorization)
  * [Implemented API](#implemented-api)
    * [Guest](#guest)
    * [Authenticated users](#authenticated-users)
    * [Customer](#customer-1)
    * [Manager](#manager-1)
    * [Admin](#admin-1)
* [Setting-up the project](#setting-up-the-project)
* [Additional materials](#additional-materials)
  * [Postman collection](#postman-collection)

## Story

Guest:
- Can see product, category list or exact product/category
- Can see rivews/questions 

Customer:
- All rights of Guest user
- Basket managing (adding / deleting products)
- Posibility to review products or comment reviews

Manager:
- All rights of Guest user
- Posibility to add/edit products of self category
- Posibility to ask customers questions

Admin:
- Rights of Customer and Manager users
- Posibility to add / edit / delete categories
- Roles managing (grant/revoke manager/admin roles)

## API
This section describes all information about the implemented API.

### Models
This section describes DTO models to link through documenting the [endpoints](#implemented-api).

#### Users

##### User
<pre>
{
    "id": 12,
    "name": "Beb Bebebb",
    "role": "BBBEEE",
    "image": 2
}
</pre>

##### Manager
<pre>
{
    "id": 12,
    "name": "Beb Bebebb",
    "role": "MANAGER",
    "category": "Be",
    "image": 2
}
</pre>

#### Comment
Used for commenting reviews and answering the questions.
<pre>
{
    "id": 1,
    "user": {
        "id": 3,
        "name": "hehe",
        "role": "CUSTOMER",
        "image": 2
    },
    "text": "text4",
    "date": "2022-12-09@11:06:14+0000"
}
</pre>

#### Category
<pre>
{
    "id": 1,
    "name": "cat1",
    "basicCharacteristics": [
        "be",
        "bebe"
    ]
}
</pre>

#### Review
<pre>
{
    "id": 1,
    "user": {
        "id": 3,
        "name": "ehe",
        "role": "CUSTOMER",
        "image": 2
    },
    "text": "text",
    "grade": 1,
    "product": 1,
    "comments": [
        {
            "id": 1,
            "user": {
                "id": 3,
                "name": "hehe",
                "role": "CUSTOMER",
                "image": 2
            },
            "text": "text4",
            "date": "2022-12-09@11:06:14+0000"
        }
    ],
    "date": "2022-12-09@11:06:11+0000"
}
</pre>

#### Question
<pre>
{
    "id": 2,
    "user": {
        "id": 3,
        "name": "bebebe",
        "role": "CUSTOMER",
        "image": 2
    },
    "text": "question",
    "product": 1,
    "comments": [
        {
            "id": 2,
            "user": {
                "id": 2,
                "name": "be",
                "role": "MANAGER",
                "image": 1
            },
            "text": "q answear",
            "date": "2022-12-09@11:07:04+0000"
        }
    ],
    "date": "2022-12-09@11:06:54+0000"
}
</pre>

#### Product:
<pre>
{
    "id": 1,
    "image": 1,
    "name": "product1",
    "description": "description",
    "price": 123.45,
    "characteristics": [
        {
            "characteristic": "be",
            "value": "be"
        },
        {
            "characteristic": "bebe",
            "value": "bebe"
        }
    ]
}
</pre>

### Exceptions

All exceptions in API have next structure:
<pre>
{
    "errorCode": "snake_case_meaningful_string",
    "errorMessage": "English user friendly message"
}
</pre>

You can face next exceptions during using the API:

<table>
  <thead align="center">
    <tr>
      <td>Http Response Status</td>
      <td>Group (use case)</td>
      <td>Error Code</td>
      <td>Error Message</td>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td align="center" rowspan="8">400<br>(Business validation)</td>
      <td align="center">Category</td>
      <td align="center">category_is_in_use</td>
      <td>There are entities in the system that use the category.</td>
    </tr>
    <tr>
      <td align="center">Image</td>
      <td align="center">temporary_store_failed</td>
      <td>Access error - temporary store has failed.</td>
    </tr>
    <tr>
      <td align="center">Product</td>
      <td align="center">basic_characteristics_not_covered</td>
      <td>Product characteristic list should contain at least basic category characteristics.</td>
    </tr>
    <tr>
      <td align="center" rowspan="2">Report</td>
      <td align="center">incompatible_review</td>
      <td>Review by given id does not apply to given product.</td>
    </tr>
    <tr>
      <td align="center">incompatible_question</td>
      <td>Question by given id does not apply to given product.</td>
    </tr>
    <tr>
      <td align="center" rowspan="3">User</td>
      <td align="center">user_has_another_role</td>
      <td>User by given identifier already has another role.</td>
    </tr>
    <tr>
      <td align="center">user_self_revoking</td>
      <td>User can not revoke himself.</td>
    </tr>
    <tr>
      <td align="center">foreign_category</td>
      <td>Product by given id has another than user's category.</td>
    </tr>
    <tr>
      <td align="center" rowspan="8">401<br>(Authentication)</td>
      <td align="center" rowspan="8">Auth</td>
      <td align="center">not_authenticated</td>
      <td>You are not authenticated to perform this action.</td>
    </tr>
    <tr>
      <td align="center">bad_facebook_token</td>
      <td>Invalid OAuth access token - cannot parse access token.</td>
    </tr>
    <tr>
      <td align="center">bad_token</td>
      <td>Token does not start with 'Bearer'.</td>
    </tr>
    <tr>
      <td align="center">expired_token</td>
      <td>Token has expired.</td>
    </tr>
    <tr>
      <td align="center">bad_token_signature</td>
      <td>Given JWT signature does not match locally computed signature.</td>
    </tr>
    <tr>
      <td align="center">malformed_token</td>
      <td>Unable to read JWT JSON value.</td>
    </tr>
    <tr>
      <td align="center">unsupported_token</td>
      <td>JWT in a particular format/configuration that does not match the format expected by the application.</td>
    </tr>
    <tr>
      <td align="center">bad_credentials</td>
      <td>Incorrect credentials.</td>
    </tr>
    <tr>
      <td align="center">403<br>(Access denied)</td>
      <td align="center">Auth</td>
      <td align="center">not_authenticated</td>
      <td>You are not authenticated to perform this action.</td>
    </tr>
    <tr>
      <td align="center" rowspan="5">404<br>(Wrong identifier)</td>
      <td align="center">Category</td>
      <td align="center">category_not_found</td>
      <td>Category by given id was not found.</td>
    </tr>
    <tr>
      <td align="center">Image</td>
      <td align="center">image_not_found</td>
      <td>Image by given id was not found.</td>
    </tr>
    <tr>
      <td align="center" rowspan="2">Report</td>
      <td align="center">review_not_found</td>
      <td>Review by given identifier was not found.</td>
    </tr>
    <tr>
      <td align="center">question_not_found</td>
      <td>Question by given identifier was not found.</td>
    </tr>
    <tr>
      <td align="center">User</td>
      <td align="center">user_not_found</td>
      <td>User by given identifier was not found.</td>
    </tr>
  </tbody>
</table>

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

With required data parameter `{"facebookToken": "EAAHC..."}`.

As the response you will get JSON Web Token as `{"token": "eyJhb..."}`.

The possible exceptions are:
- 401 - bad_facebook_token_exception
- 401 - bad_credentials

Gained JWT should be put in request header as a parameter `Authorization` with value `Bearer eyJhb...`.

#### Edge cases

You can face next exceptions while using JWT within youth request:
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

##### Authentication

Pass your facebook token to next endpoint to get JWT.

Request: `POST /authentication` with body
- `facebookToken` - token obtained from facebook

Response:
<pre>
{
    "token": "jwttoken..."
}
</pre>

Possible [exception](#exceptions) groups:
- Auth

##### Get all categories

Request: `GET /categories`

Response: list of all [categories](#category).

##### Get category by id

Request: `GET /categories/{category-id}`

Path parameters:
- `category-id` - id of needed category to get

Response: with [category dto](#category).

Possible [exception](#exceptions) groups:
- Category

##### Get all products list

Request: `GET /products`

Response: list of all [products](#product).

##### Get products by category

Request: `GET /products?category=1`

URL parameters:
- `category` - id of category to get products with

Response: list of all [products](#product) with given `category`.

Possible [exception](#exceptions) groups:
- Category

##### Get product by id

Request: `GET /products/{product-id}`

Path parameters:
- `product-id` - id of product to get

Response: with [product dto](#product)

Possible [exception](#exceptions) groups:
- Product

##### Get product reviews

Request: `GET /products/{product-id}/reviews`

Path parameters:
- `product-id` - id of product to get reviews from

Response: list of all [reviews](#review) with given `product-id`.

Possible [exception](#exceptions) groups:
- Product

##### Get product questions

Request: `GET /products/{product-id}/questions`

Path parameters:
- `product-id` - id of product to get questions from

Response: list of all [questions](#question) with given `product-id`.

Possible [exception](#exceptions) groups:
- Product

##### Get image

Request: `GET /images/{image-id}`

Path parameters:
- `image-id` - id of image to get

Response: produces JPEG image.

Possible [exception](#exceptions) groups:
- Image

#### Authenticated users

Such users as customer, manager etc. who have passed authentication can perform next actions no matter are they authorized or not.

By the default, user who have passed authentication and have not record about himself in the database will be recorded as new user with:
- role - CUSTOMER
- email - the one that is assigned in facebook
- name - will be set same as email
- image - will be set the default profile image

In order to change `name` or `image` (profile picture) authenticated users can perform [`/POST me`](#edit-information-about-yourself) endpoint.

##### Add image

Request: `POST /images` with body:
- `image` - key followed by the image file value
- `name` - name of image to set

Response:
<pre>
{
    "id": 3,
    "name": "bebebe"
}
</pre>

Possible [exception](#exceptions) groups:
- Image

##### Get user by id

Request: `GET /users/{user-id}`

Path parameters:
- `user-id` - id of user to get

Response: with [user](#user) or [manager](#manager) dto depending on user role.

Possible [exception](#exceptions) groups:
- User

##### Get user reviews by id

Request: `GET /users/{user-id}/reviews`

Path parameters:
- `user-id` - id of user to get assigned reviews

Response: with list of users [reviews](#review).

Possible [exception](#exceptions) groups:
- User

##### Get user questions by id

Request: `GET /users/{user-id}/questions`

Path parameters:
- `user-id` - id of user to get assigned questions

Response: with list of users [questions](#question).

Possible [exception](#exceptions) groups:
- User

##### Get information about yourself

Request: `GET /me`

Response: with [user](#user) or [manager](#manager) dto depending on user role with `email` field.

##### Edit information about yourself

Request: `POST /me` with body:
- `name` - new name to set
- `image` - id of image to set

Response: with [user](#user) or [manager](#manager) dto depending on user role with `email` field.

Possible [exception](#exceptions) groups:
- Image
- User

##### Applying for reviews

Request: `POST /product/{product-id}/reviews/{review-id}/comments` with body:
- `text` - text of comment to apply

Path parameters:
- `product-id` - id of product who owns given review
- `review-id` - id of review to apply for

Response: with affected [review dto](#review).

Possible [exception](#exceptions) groups:
- Product
- Report
- User

#### Customer

Customers can manipulate the basket and add reviews or questions to products.

##### Get basket items

Request: `GET /basket`

Response:
<pre>
{
    "basket": [
        {
            "productId": 1,
            "count": 6
        },
        {
            "productId": 2,
            "count": 3
        }
    ]
}
</pre>

Possible [exception](#exceptions) groups:
- User

##### Add item to basket

Request: 'POST /basket' with body:
- `product-id` - id of product to add
- `count` - count of product to add

Response:
<pre>
{
    "productId": 2,
    "count": 3
}
</pre>

Possible [exception](#exceptions) groups:
- Product
- User

##### Delete item from basket

Request: `DELETE /basket` with body:
- `product-id` - id of product to remove
- `count` - count of product to remove

Response:
<pre>
{
    "productId": 2,
    "count": 1
}
</pre>

Possible [exception](#exceptions) groups:
- Product
- User

##### Write own review

Request: `POST /products/{product-id}/reviews` with body:
- `text` - text of review
- `grade` - value in range [1; 5]

Path parameters:
- `product-id` - id of product to add review to

Response: with created [review dto](#review)

Possible [exception](#exceptions) groups:
- Product
- Report

##### Ask a question

Request: `POST /product/{product-id}/questions` with body:
- `text` - text of a question

Path parameters:
- `product-id` - id of product to add question to

Response: with created [question dto](#question)

Possible [exception](#exceptions) groups:
- Product

#### Manager

Manager can manage the products.

##### Add product

Request: `POST /products` with body:
- `name` - name of product
- `price` - price of product
- `description` - description of product
- `image` - id of image to set to the new product
- `characteristics` - list of product characteristics, each element has:
  - `characteristic` - name of characteristic
  - `value` - value of characteristic

Response: with created [product dto](#product)

Created product will have the same category as the manager has.

Possible [exception](#exceptions) groups:
- Image
- Product
- User

##### Edit product

Request: `POST /products/{product-id}` with body:
- `name` - name of product
- `price` - price of product
- `description` - description of product
- `image` - id of image to set to the new product
- `characteristics` - list of product characteristics, each element has:
  - `characteristic` - name of characteristic
  - `value` - value of characteristic

Path parameters:
- `product-id` - id of product to edit

Response: with edited [product dto](#product)

Reviews and questions assigned to this product won't be affected during editing.

Possible [exception](#exceptions) groups:
- Image
- Product
- User

##### Delete product

Request: `DELETE /products/{product-id}`

Path parameters:
- `product-id` - id of product to delete

Response: with deleted [Product Dto](#product)

Reviews and questions assigned to this product will be automatically deleted.

Possible [exception](#exceptions) groups:
- Product
- User

##### Question answering

Request: `POST /products/{product-id}/questions/{question-id}/comments` with body:
- `text` - text of question to ask

Path parameters:
- `product-id` - id of product who owns given review
- `question-id` - id of review to apply for

Response: with affected [question dto](#question)

Possible [exception](#exceptions) groups:
- Product
- Report
- User

#### Admin

Admin can manage the categories and roles.

##### Add new category

Request: `/categories` with body:
- `name` - name of new category
- `basicCharacteristics` - array of basic characteristics (strings) of new category

Response: with created [category dto](#category).

##### Delete category by id

Request: `DELETE /categories/{category-id}`

Path parameters:
- `category-id` - id of category to delete

Response: with deleted [category dto](#category). 

Possible [exception](#exceptions) groups:
- Category

##### Get list of managers

Request: `GET /users/managers`

Response: with list of [managers](#manager).

##### Grant manager role by email

Request: `POST /users/managers` with body:
- `email` - facebook email of the manager
- `category` - id of categories to set

Response: with added [manager dto](#manager).

Possible [exception](#exceptions) groups:
- Category
- Image
- User

##### Edit manager category

Request: `POST /users/managers/{manager-id}` with body
- category - id of categories to setf

Path parameters:
- `manager-id` - id of manager to edit category

Response: with edited [manager dto](#manager).

Possible [exception](#exceptions) groups:
- Category
- User

##### Revoke manager role

Request: `DELETE /users/managers/{manager-id}`

Response: with deleted [manager dto](#manager).

Possible [exception](#exceptions) groups:
- User

##### Get list of admins

Request: `GET /users/admins`
Response: with list of [admins](#admin).

##### Grant admin role by email

Request: `POST /users/admins` with body:
- `email` - facebook email of the admin

Response: with added [admin dto](#admin).

Possible [exception](#exceptions) groups:
- Image
- User

##### Revoke admin role

Request: `DELETE /users/admins/{admin-id}`

Response: with deleted [admin dto](#admin).

Possible [exception](#exceptions) groups:
- User

## Setting-up the project
This section describes all needed steps to launch the application.

### Getting the project
First of all, you need to get the project. You can do this by two ways:
- [Getting zip project file](#getting-zip-project-file)
- [Cloning the repository](#cloning-the-repository)

#### Getting zip project file
To download project in zip follow the [link](https://github.com/GrEFeRFeeD/eshop-app/archive/refs/heads/main.zip).

After downloading, unzip the archive and go to __eshop-app-main__ directory.

#### Cloning the repository
To clone the repository run the console and type:

`git clone https://github.com/GrEFeRFeeD/eshop-app.git`

After cloning is done change the directory to __eshop-app-main__ by the following command:

`cd eshop-app-main`

### Configuring docker
This section describes all needed steps to launch application via docker.

#### Configuring environmental variables
Before launching the application via docker, you need to created `api.env` file with next environment variables.
The example of such file represented in `example.env` file. The needed variables are:
- `APPLICATION_PORT` - port on which application will be run
- `JWT_SECRET` - defines secret work to assign the JWT
- `JWT_VALIDITY` - validity of JWT in seconds
- `FIRST_ADMIN_EMAIL` - facebook account attached email of first admin that will be automatically added to DB
- `FACEBOOK_CLIENT_ID` - application client id provided by facebook
- `FACEBOOK_SECRET` - application secret provided by facebook
- `FACEBOOK_REDIRECT_URI` - URI to which you will be redirected after oauth2. Configures by facebook application
- `HIBERNATE_DDL_AUTO` - hibernate DDL launch mode:
  - `validate`: validates the schema, makes no changes to the database.
  - `update`: updates the schema.
  - `create`: creates the schema, destroying previous data.
  - `create-drop`: drop the schema when the SessionFactory is closed explicitly, typically when the application is stopped.
  - none: does nothing with the schema, makes no changes to the database.
- `DATABASE_PORT` - port on which database will be run
- `POSTGRES_USER` - name of default postgresql user
- `POSTGRES_PASSWORD` - password of default postgresql user
- `POSTGRES_DB` - postgresql database name

#### Configuring docker-compose
You can use the next docker-compose.yml template to configure project launching.

<pre>
version: "3.7"

services:
  application:
    # Uncomment the one:
    # image: greferfeed/eshop   # You can use remote image from docker hub
    # build: .                  # You can use build the project for your own
    ports:
      - ${APPLICATION_PORT}:${APPLICATION_PORT}
    volumes:
      - ./:/app
    env_file:
      - api.env

  postgresql:
    ports:
      - ${DATABASE_PORT}:${DATABASE_PORT}
    image: postgres:15.0
    volumes:
      - postgresql-data:/var/lib/postgres
    env_file:
      - api.env

volumes:
  postgresql-data:
</pre>

#### Launching the application
Once the `api.env` is created with proper variables and `docker-compose.yml` is ready to execute you can launch docker with application through running the following command:

`docker-compose --env-file api.env up`

# Additional materials
This section describes all additional materials for the implemented API.

## Postman collection
API has postman collection to demonstrate the implemented API.

You can find it by the [link](https://github.com/GrEFeRFeeD/eshop-app/blob/main/docs/Postman/). 
