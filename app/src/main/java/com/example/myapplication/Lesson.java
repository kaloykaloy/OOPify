package com.example.myapplication;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Lesson#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Lesson extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String lesson1 = "Abstraction in Object-Oriented Programming (OOP) in C# is the concept of hiding the complex implementation details of an object and exposing only the essential features or functionalities to the user. This simplifies the interaction with objects by providing a clear and simplified interface, allowing users to work with the object without needing to understand the underlying complexities.\n\n"
            + "Key Points of Abstraction in C#:\n"
            + "1. Defining Abstract Classes and Methods:\n"
            + "   - An abstract class is a class that cannot be instantiated on its own and is intended to be a base class for other classes. It can contain abstract methods (methods without implementation) and non-abstract methods (methods with implementation).\n"
            + "   - An abstract method is declared without an implementation in an abstract class. Subclasses that derive from this abstract class must provide their own implementation of these abstract methods.\n"
            + "2. Purpose of Abstraction:\n"
            + "   - Simplification: Abstraction allows you to reduce complexity by hiding unnecessary details and showing only the relevant parts of an object.\n"
            + "   - Encapsulation: It provides a way to encapsulate data and methods in a logical unit, exposing only the necessary components.\n"
            + "   - Reusability: Abstract classes can be reused as base classes for other classes, allowing shared code and promoting reuse.\n"
            + "   - Polymorphism: Through abstraction, you can create a common interface for a group of related classes, enabling polymorphism.";
    private String lesson2 = "Inheritance in Object-Oriented Programming (OOP) in C# is a mechanism that allows one class to inherit properties and methods from another class. It establishes a parent-child relationship between classes, where the base class (or parent class) provides common functionality, and the derived class (or child class) extends or modifies this functionality.\n\n"
            + "Key Concepts of Inheritance in C#:\n"
            + "1. Base Class (Parent Class):\n"
            + "   - This is the class whose members (fields, properties, methods) are inherited by another class.\n"
            + "   - It provides common functionality that can be reused by derived classes.\n"
            + "   - In C#, a class can only inherit from one base class (single inheritance).\n"
            + "2. Access Modifiers and Inheritance:\n"
            + "   - Members marked as public or protected in the base class can be accessed by the derived class.\n"
            + "   - Members marked as private in the base class are not accessible directly in the derived class.\n"
            + "   - The protected modifier allows the base class to share its members with derived classes while keeping them hidden from other parts of the code.";
    private String lesson3 = "Encapsulation in C# Object-Oriented Programming (OOP) is the concept of bundling the data (fields) and the methods (functions) that operate on the data into a single unit, known as a class. It restricts direct access to some of an object's components and provides controlled ways to access and modify them. This helps protect the integrity of the data by preventing unauthorized or unintended interference.\n\n"
            + "Data Hiding:\n"
            + "• Encapsulation hides the internal state of an object from the outside world and only exposes a controlled interface.\n"
            + "• Fields (variables) are typically made private to prevent direct access from outside the class.\n"
            + "• Public methods (getters and setters) or properties are used to provide controlled access to these private fields.\n\n"
            + "Access Modifiers:\n"
            + "C# provides several access modifiers to control the visibility of class members:\n"
            + "  - private: The member is accessible only within the same class.\n"
            + "  - protected: The member is accessible within the same class and derived classes.\n"
            + "  - public: The member is accessible from any other code.\n"
            + "  - internal: The member is accessible within the same assembly.\n"
            + "  - protected internal: The member is accessible within the same assembly or from derived classes.\n\n"
            + "Using these modifiers helps in defining the scope and accessibility of class members, enhancing security and reducing the likelihood of misuse.";
    private String lesson4 = "Polymorphism in C# Object-Oriented Programming (OOP) is the ability of different classes to be treated as instances of the same class through a common interface. It allows one interface to be used for a general class of actions, enabling different classes to provide their specific implementations of the interface or abstract method. This concept helps in designing systems that are easy to extend and maintain.\n\n"
            + "Types of Polymorphism:\n\n"
            + "• Compile-time Polymorphism (Method Overloading and Operator Overloading):\n"
            + "   - Also known as static polymorphism.\n"
            + "   - Achieved using method overloading or operator overloading.\n"
            + "   - The decision of which method to invoke is made at compile time.\n\n"
            + "• Run-time Polymorphism (Method Overriding):\n"
            + "   - Also known as dynamic polymorphism.\n"
            + "   - Achieved using method overriding with inheritance and interfaces.\n"
            + "   - The decision of which method to invoke is made at runtime.\n";
    private String lesson5 = "Abstraction in Object-Oriented Programming (OOP) in C# is the concept of hiding the complex implementation details of an object and exposing only the essential features or functionalities to the user. This simplifies the interaction with objects by providing a clear and simplified interface, allowing users to work with the object without needing to understand the underlying complexities.\n\n"
            + "Key Points of Abstraction in C#:\n\n"
            + "1. Defining Abstract Classes and Methods:\n"
            + "   - An abstract class is a class that cannot be instantiated on its own and is intended to be a base class for other classes. It can contain abstract methods (methods without implementation) and non-abstract methods (methods with implementation).\n"
            + "   - An abstract method is declared without an implementation in an abstract class. Subclasses that derive from this abstract class must provide their own implementation of these abstract methods.\n\n"
            + "2. Purpose of Abstraction:\n"
            + "   - Simplification: Abstraction allows you to reduce complexity by hiding unnecessary details and showing only the relevant parts of an object.\n"
            + "   - Encapsulation: It provides a way to encapsulate data and methods in a logical unit, exposing only the necessary components.\n"
            + "   - Reusability: Abstract classes can be reused as base classes for other classes, allowing shared code and promoting reuse.\n"
            + "   - Polymorphism: Through abstraction, you can create a common interface for a group of related classes, enabling polymorphism.\n";
    private String lesson6 = "Inheritance in Object-Oriented Programming (OOP) in C# is a mechanism that allows one class to inherit properties and methods from another class. It establishes a parent-child relationship between classes, where the base class (or parent class) provides common functionality, and the derived class (or child class) extends or modifies this functionality.\n\n"
            + "Key Concepts of Inheritance in C#:\n\n"
            + "3. Base Class (Parent Class):\n"
            + "   - This is the class whose members (fields, properties, methods) are inherited by another class.\n"
            + "   - It provides common functionality that can be reused by derived classes.\n"
            + "   - In C#, a class can only inherit from one base class (single inheritance).\n\n"
            + "4. Access Modifiers and Inheritance:\n"
            + "   • Members marked as public or protected in the base class can be accessed by the derived class.\n"
            + "   • Members marked as private in the base class are not accessible directly in the derived class.\n"
            + "   • The protected modifier allows the base class to share its members with derived classes while keeping them hidden from other parts of the code.\n";
    private String lesson7 = "Encapsulation in C# Object-Oriented Programming (OOP) is the concept of bundling the data (fields) and the methods (functions) that operate on the data into a single unit, known as a class. It restricts direct access to some of an object's components and provides controlled ways to access and modify them. This helps protect the integrity of the data by preventing unauthorized or unintended interference.\n\n"
            + "Data Hiding:\n"
            + "• Encapsulation hides the internal state of an object from the outside world and only exposes a controlled interface.\n"
            + "• Fields (variables) are typically made private to prevent direct access from outside the class.\n"
            + "• Public methods (getters and setters) or properties are used to provide controlled access to these private fields.\n\n"
            + "Access Modifiers:\n"
            + "C# provides several access modifiers to control the visibility of class members:\n"
            + "  - private: The member is accessible only within the same class.\n"
            + "  - protected: The member is accessible within the same class and derived classes.\n"
            + "  - public: The member is accessible from any other code.\n"
            + "  - internal: The member is accessible within the same assembly.\n"
            + "  - protected internal: The member is accessible within the same assembly or from derived classes.\n\n"
            + "Using these modifiers helps in defining the scope and accessibility of class members, enhancing security and reducing the likelihood of misuse.\n";
    private String lesson8 = "Polymorphism in C# Object-Oriented Programming (OOP) is the ability of different classes to be treated as instances of the same class through a common interface. It allows one interface to be used for a general class of actions, enabling different classes to provide their specific implementations of the interface or abstract method. This concept helps in designing systems that are easy to extend and maintain.\n\n"
            + "Types of Polymorphism:\n\n"
            + "• Compile-time Polymorphism (Method Overloading and Operator Overloading):\n"
            + "   - Also known as static polymorphism.\n"
            + "   - Achieved using method overloading or operator overloading.\n"
            + "   - The decision of which method to invoke is made at compile time.\n\n"
            + "• Run-time Polymorphism (Method Overriding):\n"
            + "   - Also known as dynamic polymorphism.\n"
            + "   - Achieved using method overriding with inheritance and interfaces.\n"
            + "   - The decision of which method to invoke is made at runtime.\n";
    private String lesson9 = "Encapsulation in C# Object-Oriented Programming (OOP) is the concept of bundling the data (fields) and the methods (functions) that operate on the data into a single unit, known as a class. It restricts direct access to some of an object's components and provides controlled ways to access and modify them. This helps protect the integrity of the data by preventing unauthorized or unintended interference.\n\n"
            + "Data Hiding:\n"
            + "• Encapsulation hides the internal state of an object from the outside world and only exposes a controlled interface.\n"
            + "• Fields (variables) are typically made private to prevent direct access from outside the class.\n"
            + "• Public methods (getters and setters) or properties are used to provide controlled access to these private fields.\n\n"
            + "Access Modifiers:\n"
            + "C# provides several access modifiers to control the visibility of class members:\n"
            + "  - private: The member is accessible only within the same class.\n"
            + "  - protected: The member is accessible within the same class and derived classes.\n"
            + "  - public: The member is accessible from any other code.\n"
            + "  - internal: The member is accessible within the same assembly.\n"
            + "  - protected internal: The member is accessible within the same assembly or from derived classes.\n\n"
            + "Using these modifiers helps in defining the scope and accessibility of class members, enhancing security and reducing the likelihood of misuse.\n";
    private String lesson10 = "Inheritance in Object-Oriented Programming (OOP) in C# is a mechanism that allows one class to inherit properties and methods from another class. It establishes a parent-child relationship between classes, where the base class (or parent class) provides common functionality, and the derived class (or child class) extends or modifies this functionality.\n\n"
            + "Key Concepts of Inheritance in C#:\n\n"
            + "5. Base Class (Parent Class):\n"
            + "   - This is the class whose members (fields, properties, methods) are inherited by another class.\n"
            + "   - It provides common functionality that can be reused by derived classes.\n"
            + "   - In C#, a class can only inherit from one base class (single inheritance).\n\n"
            + "6. Access Modifiers and Inheritance:\n"
            + "   • Members marked as public or protected in the base class can be accessed by the derived class.\n"
            + "   • Members marked as private in the base class are not accessible directly in the derived class.\n"
            + "   • The protected modifier allows the base class to share its members with derived classes while keeping them hidden from other parts of the code.\n";
    private CustomSpinner customSpinner;
    private String mParam1;
    private String mParam2;

    public Lesson() {

    }

    public static Lesson newInstance(String param1, String param2) {
        Lesson fragment = new Lesson();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lesson, container, false);
        customSpinner = view.findViewById(R.id.levelSpinner);
        TextView lesson = view.findViewById(R.id.lessonContent);
        TextView lessonLvl = view.findViewById(R.id.lessonId);
        lessonLvl.setText("Lesson 1");
        lesson.setText(lesson1);
        String[] levels = new String[10];
        for (int i = 1; i <= 10; i++) {
            levels[i - 1] = "Level " + i;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, levels);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        customSpinner.setAdapter(adapter);
        customSpinner.setOnItemSelectedListener(selectedItem -> {

            if (selectedItem.equals("Level 1")) {
                lessonLvl.setText("Lesson 1");
                lesson.setText(lesson1);
            } else if (selectedItem.equals("Level 2")) {
                lessonLvl.setText("Lesson 2");
                lesson.setText(lesson2);
            } else if (selectedItem.equals("Level 3")) {
                lessonLvl.setText("Lesson 3");
                lesson.setText(lesson3);
            } else if (selectedItem.equals("Level 4")) {
                lessonLvl.setText("Lesson 4");
                lesson.setText(lesson4);
            } else if (selectedItem.equals("Level 5")) {
                lessonLvl.setText("Lesson 5");
                lesson.setText(lesson5);
            } else if (selectedItem.equals("Level 6")) {
                lessonLvl.setText("Lesson 6");
                lesson.setText(lesson6);
            } else if (selectedItem.equals("Level 7")) {
                lessonLvl.setText("Lesson 7");
                lesson.setText(lesson7);
            } else if (selectedItem.equals("Level 8")) {
                lessonLvl.setText("Lesson 8");
                lesson.setText(lesson8);
            } else if (selectedItem.equals("Level 9")) {
                lessonLvl.setText("Lesson 9");
                lesson.setText(lesson9);
            } else if (selectedItem.equals("Level 10")) {
                lessonLvl.setText("Lesson 10");
                lesson.setText(lesson10);
            }

        });


        return view;
    }
}
