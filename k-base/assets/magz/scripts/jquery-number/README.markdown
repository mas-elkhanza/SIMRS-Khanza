# jQuery Number Plugin

### By Sam Sehnert, [Custom D](https://www.customd.com/) 2015

This is a [jQuery](http://jquery.com/) plugin which allows developers to easily format numbers 
for display use. Allows users to replace numbers inline in a document, or return a formatted number
for other uses.

Requires jQuery 1.6 or greater.

## Documentation

See our [jQuery Number Format article](https://www.customd.com/articles/14/jquery-number-format-redux) for more information.

### Basic number formatting

The number method takes up to four parameters, but only the first one is required.

1. Number: The number you want to format.

    ```javascript
    $.number( 5020.2364 ); // Outputs 5,020
    ```
2. Decimal Places: The number of decimal places you wish to see. Defaults to 0.

	```javascript		
    $.number( 5020.2364, 2 ); // Outputs: 5,020.24
    ```		
3. Decimal Separator: The character(s) to use as a decimal separator. Defaults to '.'.

    ```javascript		
    $.number( 135.8729, 3, ',' ); // Outputs: 135,873
    ```
4. Thousands Separator: The character(s) to use as a thousands separator. Defaults to ','.

    ```javascript
    $.number( 5020.2364, 1, ',', ' ' ); // Outputs: 5 020,2	
    ```

### Number formatting as-you-type

When targeting a collection of input elements, you can have the number format plugin automatically format the users
input based on your format settings.
```javascript
$('input.number').number( true, 2 );
```

Passing `true` to the number parameter, indicates that you want the value of the field or element to be effected, instead
of placing the passed number into the field or element. All other parameters are as decribed above.

When the user types, their input will automatically be converted in the correct format. This also attaches [.val() hooks](http://blog.jquery.com/2011/05/03/jquery-16-released/)
which allow you to continue using `.val()` to set your input elements, and you don't need to worry about handling the formatting.

Automatic paste formatting is also supported. 


### Writing numbers into an element
```javascript
$('.selector').number( 1234, 2 ); // Changes the text value of the element matching selector to the formatted number.
```

### Formatting numbers within a collection of elements

Assuming we have the following structure:
```html
<p>
  <span class="number">1025.8702</span>
  <span class="number">18023</span>
  <span class="number">982.3</span>
  <span class="number">.346323</span>
</p>
```

We can use this JavaScript:
```javascript
// the 'true' signals we should read and replace the text contents of the target element.
$('span.number').number( true, 2 )
```
And come away with this result:
```html
<p>
  <span class="number">1,025.87</span>
  <span class="number">18,023.00</span>
  <span class="number">982.30</span>
  <span class="number">0.35</span>
</p>
```
