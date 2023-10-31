package com.blanc.market.order.service;

import com.blanc.market.Product_temp.Product_temp;
import com.blanc.market.User_temp.User_temp;
import com.blanc.market.order.dto.OrderRequest;
import com.blanc.market.order.entity.Order;
import com.blanc.market.order.entity.OrderProduct;
import com.blanc.market.order.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @PersistenceContext EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void do_order() throws Exception{

        //given
        User_temp user = createUser("user1");
        Product_temp product = createProduct("p1",10000);
        OrderRequest orderRequest1 = new OrderRequest(user.getId(), product.getId(), product.getPrice(),1);

        //when
        Long orderId = orderService.order(orderRequest1);

        //then
        Order getOrder = orderRepository.findOne(orderId);
        User_temp getUser1 = getOrder.getUser();
        String user1Name = getUser1.getName();

        List<OrderProduct> getProduct1 = getOrder.getOrderProducts();


        System.out.println("test_product" + getProduct1); //제품 뽑기?

        assertEquals("상품 주문자 일치","user1",user1Name);


    }



    private User_temp createUser(String name){
        User_temp user = new User_temp();
        user.setName(name);
        em.persist(user);
        return user;
    }

    private Product_temp createProduct(String name, int price){
        Product_temp product = new Product_temp();
        product.setName(name);
        product.setPrice(price);
        em.persist(product);
        return product;
    }

}