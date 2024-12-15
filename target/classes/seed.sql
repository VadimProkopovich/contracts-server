-- Таблица пользователей
CREATE TABLE "user"
(
    id       SERIAL PRIMARY KEY,        -- Уникальный идентификатор
    username VARCHAR(50)  NOT NULL,     -- Логин
    password VARCHAR(255) NOT NULL,     -- Хэш пароля
    email    VARCHAR(100),              -- Email (опционально)
    role     VARCHAR(20) DEFAULT 'user' -- Роль (admin, manager и т.д.)
);

-- Таблица лидов
CREATE TABLE Lead
(
    id          SERIAL PRIMARY KEY,                   -- Уникальный идентификатор
    name        VARCHAR(100) NOT NULL,                -- Имя клиента или название компании
    contact     VARCHAR(255),                         -- Контактная информация
    source      VARCHAR(50),                          -- Источник лида
    status      VARCHAR(20) DEFAULT 'new',            -- Статус лида
    assigned_to INT REFERENCES "user" (id),           -- Ответственный пользователь
    created_at  TIMESTAMP   DEFAULT CURRENT_TIMESTAMP -- Дата создания
);

-- Таблица договоров
CREATE TABLE Contract
(
    id            SERIAL PRIMARY KEY,                    -- Уникальный идентификатор
    lead_id       INT REFERENCES Lead (id),              -- Связанный лид
    category_id   INT REFERENCES ContractCategory (id),  -- Категория договора
    template_id   INT REFERENCES DocumentTemplate (id),  -- Шаблон документа
    status        VARCHAR(20) DEFAULT 'draft',           -- Статус договора
    amount        NUMERIC(10, 2),                        -- Сумма договора
    name          VARCHAR(100) NOT NULL,                 -- Название договора
    description   TEXT,                                  -- Описание договора
    document_link TEXT,                                  -- Ссылка на документ
    created_by    INT REFERENCES "user" (id),            -- Пользователь, создавший договор
    updated_by    INT REFERENCES "user" (id),            -- Последний, кто обновил договор
    created_at    TIMESTAMP   DEFAULT CURRENT_TIMESTAMP, -- Дата создания
    updated_at    TIMESTAMP   DEFAULT CURRENT_TIMESTAMP  -- Дата обновления
);

-- Таблица источников лидов
CREATE TABLE LeadSource
(
    id          SERIAL PRIMARY KEY,   -- Уникальный идентификатор
    name        VARCHAR(50) NOT NULL, -- Название источника
    description TEXT,                 -- Описание источника (опционально)
    is_active   BOOLEAN DEFAULT TRUE  -- Активность источника
);

-- Таблица категорий договоров
CREATE TABLE ContractCategory
(
    id          SERIAL PRIMARY KEY,                 -- Уникальный идентификатор
    name        VARCHAR(50) NOT NULL,               -- Название категории
    description TEXT,                               -- Описание категории
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- Дата создания
);

-- Таблица уведомлений
CREATE TABLE Notification
(
    id         SERIAL PRIMARY KEY,                  -- Уникальный идентификатор
    message    TEXT NOT NULL,                       -- Текст уведомления
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Дата создания
    expires_at TIMESTAMP,                           -- Дата истечения актуальности
    is_read    BOOLEAN   DEFAULT FALSE              -- Статус прочтения
);

-- Таблица платёжных пакетов
CREATE TABLE PaymentPackage
(
    id          SERIAL PRIMARY KEY,      -- Уникальный идентификатор
    name        VARCHAR(50)    NOT NULL, -- Название пакета
    price       NUMERIC(10, 2) NOT NULL, -- Цена пакета
    description TEXT                     -- Описание пакета
);

-- Таблица шаблонов документов
CREATE TABLE DocumentTemplate
(
    id         SERIAL PRIMARY KEY,                 -- Уникальный идентификатор
    name       VARCHAR(50) NOT NULL,               -- Название шаблона
    template   TEXT        NOT NULL,               -- Текст шаблона
    created_by INT REFERENCES "user" (id),         -- Кто добавил шаблон
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- Дата добавления
);