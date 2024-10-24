package com.RODS.utils;



public interface Constants {
    // for pagination only
    String NULL = "null";
    String NAME = "name";

    // for logging
    String CATEGORIES_ALL = "select.all.categories.log";

    String DISHES_ALL_PAGE = "select.all.dishes.page.log";
    String DISHES_ALL = "select.all.dishes.log";
    String DISHES_CREATE = "create.dishes.log";
    String DISHES_CREATE_DBE = "create.dishes.dbe";
    String DISHES_ONE = "select.dishes.log";
    String DISHES_ONE_DBE = "select.dishes.dbe";
    String DISHES_UPDATE = "update.dishes.log";
    String DISHES_UPDATE_DBE = "update.dishes.dbe";
    String DISHES_DELETE = "delete.dishes.log";
    String DISHES_DELETE_DBE = "delete.dishes.dbe";

    String BASKET_ALL = "select.all.baskets.log";
    String BASKET_ALL_EMPTY = "select.all.baskets.empty";
    String BASKET_CREATE = "create.basket.log";
    String BASKET_CREATE_DBE = "create.basket.dbe";
    String BASKET_DELETE = "delete.basket.log";
    String BASKET_DELETE_DBE = "delete.basket.dbe";
    String BASKET_DELETE_ALL = "delete.all.basket.log";
    String BASKET_DELETE_ALL_DBE = "delete.all.basket.dbe";

    String ORDERS_ALL = "select.all.orders.log";
    String ORDERS_ALL_MANAGER = "select.all.orders.manager.log";
    //    String ORDERS_ONE = "select.orders.log";
    String ORDERS_NOT_FOUND = "select.orders.dbe";
    String ORDERS_CREATE = "create.order.log";
    String ORDERS_UPDATE = "update.orders.log";

}
