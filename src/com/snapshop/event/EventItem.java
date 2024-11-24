/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.snapshop.event;

import com.snapshop.model.ModelItem;
import java.awt.Component;

/**
 *
 * @author lucas
 */
public interface EventItem {
    
    public void itemClick(Component com, ModelItem item);
    
}
