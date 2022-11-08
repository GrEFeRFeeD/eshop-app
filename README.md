# eshop-app

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

Категории товаров:
- Clothes
- Electronics
- Personal Care
- Hobbies
- Equipment
- Food Products
- Agriculture

## Models

### User
<pre>
{
    "id": 12,
}
</pre>

### Comment
Used for commenting reviewes and answearing the questions.
<pre>
{
    "id": 12,
}
</pre>

### Review
<pre>{
    "id": 123,
    "text": "b e b e",
    "comments": [
      
    ]
}</pre>

### Question
<pre>
{
    "id": 12,
}
</pre>

### Product:
<pre>{
    "id": 123,
    "name": "be",
    "price": 123.45,
    "category": "CAT",
    "description": "bebebe bebe bebebebeb",
    "reviews": [
    ]
    "questions":
    ]
}</pre>
