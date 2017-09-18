		// строка в таблице как WebElement списка
        List<WebElement> orderTable = driver.findElements(By.cssSelector("#order_confirmation-wrapper tr"));
		// количество строчек в таблице
		int totalQuantityOfRows = orderTable.size();
		// количество строчек заказа =  количество строчек в таблице - 5
        int orderQuantityOfRows = orderTable.size() - 5;

        for (int i = 0; i <= orderQuantityOfRows; i++) {
            WebElement removeButton = driver.findElement(By.cssSelector("[name=remove_cart_item]"));
            removeButton.click();
		// подождать пока количество строчек в таблице не будет меньше на 1
            wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector("#order_confirmation-wrapper tr"), totalQuantityOfRows--));
