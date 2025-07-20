-- =============================================
-- Grant permissions for banking_cmd_user on command_db
-- =============================================
GRANT CONNECT ON DATABASE command_db TO banking_cmd_user;
GRANT CREATE ON DATABASE command_db TO banking_cmd_user;
GRANT USAGE, CREATE ON SCHEMA public TO banking_cmd_user;

ALTER DEFAULT PRIVILEGES IN SCHEMA public
  GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO banking_cmd_user;

-- =============================================
-- Grant permissions for banking_cmd_user on query_db
-- =============================================
GRANT CONNECT ON DATABASE query_db TO banking_cmd_user;
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO banking_cmd_user;

ALTER DEFAULT PRIVILEGES IN SCHEMA public
  GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO banking_cmd_user;

-- =============================================
-- Grant read-only permissions for banking_query_user on query_db
-- =============================================
GRANT CONNECT ON DATABASE query_db TO banking_query_user;
GRANT USAGE ON SCHEMA public TO banking_query_user;
GRANT SELECT ON ALL TABLES IN SCHEMA public TO banking_query_user;

ALTER DEFAULT PRIVILEGES IN SCHEMA public
  GRANT SELECT ON TABLES TO banking_query_user;

-- =============================================
-- Grant write permissions for banking_cmd_user on auth_db
-- =============================================
GRANT CONNECT ON DATABASE auth_db TO banking_cmd_user;
GRANT USAGE, CREATE ON SCHEMA public TO banking_cmd_user;

GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO banking_cmd_user;

ALTER DEFAULT PRIVILEGES IN SCHEMA public
  GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO banking_cmd_user;

-- =============================================
-- Grant read-only permissions for banking_auth_user on auth_db
-- =============================================
GRANT CONNECT ON DATABASE auth_db TO banking_auth_user;
GRANT USAGE ON SCHEMA public TO banking_auth_user;
GRANT SELECT ON ALL TABLES IN SCHEMA public TO banking_auth_user;

ALTER DEFAULT PRIVILEGES IN SCHEMA public
  GRANT SELECT ON TABLES TO banking_auth_user;
