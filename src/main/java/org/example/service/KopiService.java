/*
 * Copyright (c) 2016, WSO2 Inc. (http://wso2.com) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.example.service;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;


/**
 * This is the Microservice resource class.
 * See <a href="https://github.com/wso2/msf4j#getting-started">https://github.com/wso2/msf4j#getting-started</a>
 * for the usage of annotations.
 *
 * @since 0.1
 */
@Path("/service")
public class KopiService {
        private Map<String, Order> ordersList = new ConcurrentHashMap<String, Order>();
        private final Map<String, Double> priceList = new ConcurrentHashMap<String, Double>();
        private static final Random rand = new Random();

        @POST
        @Path("/{order}")
        @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
        @Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
        public Response addOrder(Order orderBean) {
            String drinkName = orderBean.getDrinkName();
            String additions = orderBean.getAdditions();
            orderBean.setCost(calculateCost(drinkName, additions));
            ordersList.put(orderBean.getOrderId(), orderBean);
            ordersList.put(orderBean.getOrderId(), orderBean);
            return Response.ok(orderBean).build();

        }
    @GET
    @Path("/order/{orderId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
    public Order getOrder(@PathParam("orderId") String id) {
        return ordersList.get(id);
    }
        private double calculateCost(String drinkName, String additions) {
            double cost = getPrice(drinkName, false);
            if (additions != null && !"".equals(additions)) {
                String[] additionalItems = additions.split(" ");
                for (String item : additionalItems) {
                    cost += getPrice(item, true);
                }
            }
            return Double.parseDouble(Order.currencyFormat.format(cost));
        }

        /**
         * This method is a utility method used to help calculate the price of a drink
         *
         * @param item     the name of the drink
         * @param addition whether additions are present or not
         * @return the price of the item
         */
        private double getPrice(String item, boolean addition) {
            synchronized (priceList) {
                Double price = priceList.get(item);
                if (price == null) {
                    if (addition) {
                        price = rand.nextDouble() * 5;
                    } else {
                        price = rand.nextInt(8) + 2 - 0.01;
                    }
                    priceList.put(item, price);
                }
                return price;
            }
        }
}
