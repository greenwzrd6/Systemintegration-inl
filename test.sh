#!/bin/bash

echo "First test"
echo ""
echo "Getting json for substitute_id=3"
curl -s "http://localhost:8080/v1?format=json&substitute_id=3" | jq '.' &> /dev/null || fail "json is not valid for substitute_id=3"
count_anders=$(curl -s "http://localhost:8080/v1?format=json&substitute_id=3" | jq '.' | grep Anders | wc -l)
if [ $count_anders != 3 ]
then
    fail "Wordcount should count 3 Anders, instead got: $count_anders"
fi
echo "Test passed!"
echo ""
echo "------------"
echo ""
echo "Second test"
echo ""
echo "Getting xml for day=2018-01-15"
curl -s "http://localhost:8080/v1?format=xml&day=2018-01-15" | xmllint - &> /dev/null || fail "XML is invalid for substitute_id=1 and day=2018-01-15"
count_address=$(curl -s "http://localhost:8080/v1?format=xml&day=2018-01-15" | xmllint - | grep 'Lärdomsgatan 3 402 72 GÖTEBORG' | wc -l)
if [ $count_address != 1 ]
then
    fail "Wordcount should count address 1 time, instead got: $count_address"
fi
echo "Test passed!"
