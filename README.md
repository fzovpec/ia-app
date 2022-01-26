A school app for writting report cards.

# Functionallity
1. Import report cards
2. Modify report cards
3. Export report cards

# Data import and export format
All the data is imported and exported from the app in an XML file of the following format:
```
<root>
  <report>
    <studFN>Student first name</studFN>
    <studLN>Student last name</studLN>
    <year>Year of the report card</year>
    <term>Term of the report card</term>
    <sectName>D</sectName>
    <courseName>Course name</courseName>
    <bin1>First grade</bin1>
    <bin2>Second grade grade</bin2>
    <bin3>Third grade</bin3>
    ...
    <binN>Nth grade</binN>
    <coef>Subject coefficient</coef>
    <comment>Report card comment</comment>
  </report>
</root>
```

# Libraries used
1. Derby Database
2. Java AWT
3. Mig Layout
