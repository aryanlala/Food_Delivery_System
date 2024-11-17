# Food_Delivery_System
API-Based Products Asssignment

## Restaurant Owner flow:
1. Restaurant owner needs to register using /api/v1/register endpoint and specify role as RESTAURANT_OWNER
2. using login endpoint generate token
3. paste generated token in Authorize section of swagger and use the token to access the endpoints.



## Build docker image
docker build --tag=food-delivery:latest .

## Run docker image
docker run -p 9090:9090 food-delivery:latest
