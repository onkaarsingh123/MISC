package com.psl.data;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.psl.bean.Product;

@XmlRootElement(name="product-db")
public class ProductDataStore {

	private List<Product> productDataStore;
	private AtomicInteger productId;
	
	public ProductDataStore() {
	
		productDataStore=new CopyOnWriteArrayList<Product>();
		productId=new AtomicInteger();
	}

	@XmlElement(name="product")
	@XmlElementWrapper(name="products")
	public List<Product> getProductDataStore() {
		return productDataStore;
	}

	public void setProductDataStore(List<Product> productDataStore) {
		this.productDataStore = productDataStore;
	}

	public AtomicInteger getProductId() {
		return productId;
	}

	public void setProductId(AtomicInteger productId) {
		this.productId = productId;
	}
	
	public int addProduct(Product product){
		
		int newProductId=productId.incrementAndGet();
		Product newProduct=new Product(newProductId,product.getName(),product.getDescription(),product.getPrice());
		productDataStore.add(product);
		return newProductId;
	}
	
}
