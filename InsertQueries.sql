INSERT INTO pizzas(pizza_name, ingredients, size, price)
VALUES
('BBQ chicken', 'BBQ SAUCE, MOZZARELLA, CHICKEN BREAST, RED ONION, PEPPERS', 'S', 8.40),
('BBQ chicken', 'BBQ SAUCE, MOZZARELLA, CHICKEN BREAST, RED ONION, PEPPERS', 'M', 11.12),
('Meatball', 'MOZZARELLA, RICOTTA, MEATBALLS, PEPPERS, RED ONION, MICRO BASIL', 'S', 10.10),
('Meatball', 'MOZZARELLA, RICOTTA, MEATBALLS, PEPPERS, RED ONION, MICRO BASIL', 'M', 12.10);

INSERT INTO salads(salad_name, ingredients, price)
VALUES('Garden salad', 'Romaine, iceberg, red leaf lettuce, Roma tomatoes, cucumbers, red onions, carrots & croutons', 6.50),
('Caesar salad', 'Chopped romaine tossed in Caesar dressing, croutons & freshly grated Parmesan cheese', 4.20),
('Santa Barbara cobb', 'Crisp iceberg & romaine lettuce, tomatoes, avocado, blue cheese, crisp bacon & egg', 5.50);

INSERT INTO drinks(drink_type, brand, quantity, price)
VALUES('Beer', 'Kamenitza', '500', 3),
('Wine', 'Rose', '500', 4),
('Water', 'Devin', '500', 3),
('Alcohol-free', 'Coca-Cola', '1000', 2.50);

INSERT INTO users(username, password, phone, email)
VALUES('alexander.98', 'userPassword@1', '0894089756', 'alexander.98@gmail.com'),
('ivan_petrov7', 'userPassword@2', '0897568948', 'ivan7_petrov@gmail.com');

INSERT INTO admins(adminName, password, phone, email)
VALUES('TodorPetrov8', 'adminPassword@1', '0886269718', 'todor78Kocev@gmail.com'),
('Georgi9Kulev', 'adminPassword@2', '0897586147', 'kulev_georgi79@gmail.com');