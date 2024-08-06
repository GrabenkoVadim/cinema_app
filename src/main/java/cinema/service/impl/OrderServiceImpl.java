package cinema.service.impl;

import cinema.model.User;
import cinema.dao.OrderDao;
import cinema.lib.Inject;
import cinema.lib.Service;
import cinema.model.Order;
import cinema.model.ShoppingCart;
import cinema.service.OrderService;
import cinema.service.ShoppingCartService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Inject
    private OrderDao orderDao;
    @Inject
    private ShoppingCartService shoppingCartService;

    private static void accept(Order order) {
    }

    @Override
    public Order completeOrder(ShoppingCart cart) {
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setTickets(new ArrayList<>(cart.getTickets()));
        order.setOrderDate(LocalDateTime.now());
        shoppingCartService.clearShoppingCart(cart);
        return orderDao.add(order);
    }

    @Override
    public List<Order> getOrdersHistory(User user) {
        return orderDao.getByUser(user);
    }
}
