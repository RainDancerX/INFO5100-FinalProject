/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.snapshop.event;

import com.snapshop.model.ModelItem;

/**
 *
 * @author lucas
 */
public interface AddToCartListener {

    void onAddToCart(ModelItem item, int quantity);
}
