INSERT INTO pizzas(pizza_name, size, ingredients, price)
VALUES('BBQ CHICKEN', 'S', 'BBQ SAUCE, MOZZARELLA, CHICKEN BREAST, RED ONION, PEPPERS', 8.40),
('BBQ CHICKEN', 'M', 'BBQ SAUCE, MOZZARELLA, CHICKEN BREAST, RED ONION, PEPPERS', 11.12),
('MEATBALL', 'S', 'MOZZARELLA, RICOTTA, MEATBALLS, PEPPERS, RED ONION, MICRO BASIL', 10.00);

INSERT INTO salads(salad_name, ingredients, price)
VALUES('GARDEN SALAD', 'Romaine, iceberg, red leaf lettuce, Roma tomatoes, cucumbers, red onions, carrots & croutons', 6.50),
('CAESAR SALAD', 'Chopped romaine tossed in Caesar dressing, croutons & freshly grated Parmesan cheese', 5.20),
('SANTA BARBARA COBB', 'Crisp shredded iceberg & romaine lettuce, tomatoes, avocado, blue cheese, crisp bacon & egg', 5.50);

INSERT INTO drinks(drink_type, brand, quantity, price)
VALUES('BEER', 'Kamenitza', 500, 3),
('WINE', 'Rose', 250, 4),
('WATER', 'Devin', 500, 3),
('alcohol-free', 'Coca-Cola', 1000, 2.50);

INSERT INTO users(username, password, phone, email)
VALUES('alexander98', 'userPassword@1', '0894089756', 'alexander452@gmail.com'),
('ivan_petrov7', 'userPassword@2', '0897568948', 'ivan7_petrov@gmail.com');

INSERT INTO admins(adminName, password, phone, email)
VALUES('TodorKocev8', 'adminPassword@1', '0886269718', 'todor78Kocev@gmail.com'),
('Georgi96Kulev', 'adminPassword@2', '0897586147', 'kulev_georgi79@gmail.com');