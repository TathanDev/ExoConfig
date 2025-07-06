# Exo Config 0.2.0

- Modify how the description works.
  - Description on Screen requires the `@ScreenInfos.Description` field to be set.
  - The `@ScreenInfos.NoDescription` annotation was removed.


- New Config Annotation.
  - Add `@ConfigInfos.PossibleStringValues` and `@ConfigInfos.PossibleIntValues` to allow certains values for config fields.


- Add `ConfigTypesRegistry`
  - This allows for registering new types with custom JSON serialization.
  - Also allow custom post-validation logic for these types.


- Add Post Validation to configs.
  - Add `PostValidation` interface to define post-validation logic.
  - Add automatic post-validation for Custom Config Types Registry.

- Add Ranged Number Config Type
  - Allows for defining a range of valid numbers.

- Add new widget
  - Resourcelocation widget
  - Enum Widget