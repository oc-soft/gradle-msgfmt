# msgfmt for gradle

This project provides a gradle plugin to generage message object which is used
by gettext.

## How to use this plugin

### install

You add following lines into build.gradle.

```
plugins {
  id 'net.oc_soft.msgfmt'
}

```

You prepare some source files for msgfmt like followings.
```
src
+--i18n
   +--en
      +--LC_MESSAGES
         +messages.po
   +--ja
      +--LC_MESSAGES
         +messages.po

```

Now you can run msgfmt task.

```
./gradlew msgfmt
```

Then you have messages objects like these.

```
i18n
+--en
   +--LC_MESSAGES
      +messages.po
+--ja
   +--LC_MESSAGES
      +messages.po

```

