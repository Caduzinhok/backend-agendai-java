-- Tabela Users
CREATE TABLE users (
    id TEXT PRIMARY KEY UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL  -- "USER" ou "ADMIN"
);

-- Tabela Empresa
CREATE TABLE companies (
    id TEXT PRIMARY KEY UNIQUE NOT NULL,
    cnpj VARCHAR(50) NOT NULL,
    name VARCHAR(100) NOT NULL,
    address TEXT NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(50) NOT NULL
);

-- Tabela Serviços
CREATE TABLE services (
    id TEXT PRIMARY KEY UNIQUE NOT NULL,
    company_id TEXT NOT NULL,
    name VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    duration INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    CONSTRAINT fk_company_service FOREIGN KEY (company_id) REFERENCES companies(id) ON DELETE CASCADE
);

-- Tabela Funcionários
CREATE TABLE employees (
    id TEXT PRIMARY KEY UNIQUE NOT NULL,
    company_id TEXT NOT NULL,
    name VARCHAR(100) NOT NULL,
    cpf VARCHAR(30) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(50) NOT NULL,
    role VARCHAR(50) NOT NULL,
    CONSTRAINT fk_company_employee FOREIGN KEY (company_id) REFERENCES companies(id) ON DELETE CASCADE
);

-- Tabela de Relação Empregados - Serviços
CREATE TABLE employees_services (
    employee_id VARCHAR(255) NOT NULL,
    service_id VARCHAR(255) NOT NULL,
    CONSTRAINT fk_employee_services_employee
    FOREIGN KEY (employee_id) REFERENCES employees(id)
    ON DELETE CASCADE,
    CONSTRAINT fk_employee_services_service
    FOREIGN KEY (service_id) REFERENCES services(id)
    ON DELETE CASCADE,
    PRIMARY KEY (employee_id, service_id)
);

-- Tabela Availability (Disponibilidade de horários)
CREATE TABLE availabilities (
    id TEXT PRIMARY KEY UNIQUE NOT NULL,
    user_id TEXT NOT NULL,
    day_of_week VARCHAR(20) NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    is_blocked BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_user_availability FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Tabela Appointments (Agendamentos)
CREATE TABLE appointments (
    id TEXT PRIMARY KEY UNIQUE NOT NULL,
    user_id TEXT NOT NULL,
    service_id TEXT NOT NULL,
    employee_id TEXT NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    status VARCHAR(50) NOT NULL,
    notes TEXT NOT NULL,
    CONSTRAINT fk_user_appointment FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_service_appointment FOREIGN KEY (service_id) REFERENCES services(id) ON DELETE CASCADE,
    CONSTRAINT fk_employee_appointment FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE CASCADE
);

-- Tabela Notifications (Notificações)
CREATE TABLE notifications (
    id TEXT PRIMARY KEY UNIQUE NOT NULL,
    user_id TEXT NOT NULL,
    appointment_id TEXT NOT NULL,
    message TEXT NOT NULL,
    sent_at TIMESTAMP NOT NULL,
    is_read BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_user_notification FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_appointment_notification FOREIGN KEY (appointment_id) REFERENCES appointments(id) ON DELETE CASCADE
);
