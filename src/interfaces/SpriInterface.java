/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.util.List;

/**
 *
 * @author Administrator
 */
public interface SpriInterface<T> {
    void saveOrUpdate(T domain);
    void delete(T domain);
    List<T> findBy(Object domain);
    List<T> search(Object domain);
}
