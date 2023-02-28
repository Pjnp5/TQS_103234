package tqs.lab2;

import java.util.ArrayList;
import java.util.List;

public class StocksPortfolio {
    private final List<Stock> stocks ;
    private final IStockmarketService stockmarket;

    public StocksPortfolio(IStockmarketService stockmarket){
        this.stockmarket = stockmarket;
        this.stocks = new ArrayList<>();
    }

    public void addStock(Stock stock){
        stocks.add(stock);
    }

    public double getTotalValue(){
        double value = 0.0;
        for( Stock stock : stocks){
            value += stock.getQuantity() * stockmarket.lookUpPrice(stock.getLabel());
        }
        return value;
    }
}
