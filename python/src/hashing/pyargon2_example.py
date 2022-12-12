# https://pypi.org/project/pyargon2/

from pyargon2 import hash

password = 'a strong password'
salt = 'a unique salt'
hash1 = hash(password, salt, pepper="pepper", time_cost=1000)
print(hash1)

hash2 = hash(password, salt, pepper="pepper", time_cost=1000)
print(hash2)

assert hash1 == hash2
