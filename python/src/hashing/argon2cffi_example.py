import os

from argon2 import PasswordHasher
from argon2._password_hasher import _ensure_bytes
from argon2.low_level import hash_secret

password = 'a strong password'
salt = 'a unique salt'

ph = PasswordHasher()
hash1 = ph.hash(password)
print(hash1)

hash2 = ph.hash(password)
print(hash2)

assert hash1 != hash2

salt = "salt-salt-salt-salt-salt-salt-salt-salt-"
hash_secret1 = hash_secret(secret=_ensure_bytes(password, ph.encoding),
                           salt=bytes(salt, encoding='utf-8'),
                           time_cost=ph.time_cost,
                           memory_cost=ph.memory_cost,
                           parallelism=ph.parallelism,
                           hash_len=ph.hash_len,
                           type=ph.type,
                           ).decode("ascii")
print("hash_secret1: ", hash_secret1)

hash_secret2 = hash_secret(secret=_ensure_bytes(password, ph.encoding),
                           salt=bytes(salt, encoding='utf-8'),
                           time_cost=ph.time_cost,
                           memory_cost=ph.memory_cost,
                           parallelism=ph.parallelism,
                           hash_len=ph.hash_len,
                           type=ph.type,
                           ).decode("ascii")

print("hash_secret2: ", hash_secret2)

assert hash_secret1 == hash_secret2
